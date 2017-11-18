package com.ydscience.lifeassistant.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ydscience.lifeassistant.R;
import com.ydscience.lifeassistant.bean.news.NewDetailes;
import com.ydscience.lifeassistant.presenter.NewDetailsPersenterImpl;
import com.ydscience.lifeassistant.presenter.WeChatNewsPresenterImpl;
import com.ydscience.lifeassistant.presenter.ZhiHuInfoPresenterImpl;
import com.ydscience.lifeassistant.ui.activity.ScrollingActivity;
import com.ydscience.lifeassistant.ui.adapter.NewsInfoAdapter;
import com.ydscience.lifeassistant.ui.interfaces.IPagerNewFragment;
import com.ydscience.lifeassistant.utils.Commons;
import com.ydscience.lifeassistant.utils.MyLog;
import com.ydscience.lifeassistant.utils.ToastUtils;
import com.ydscience.lifeassistant.utils.Utils;
import com.ydscience.lifeassistant.view.RecyclerViewEmptySupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by ydsience on 2017/6/12.
 */

public class PagerNewsFragment extends BaseFragment implements IPagerNewFragment,SwipeRefreshLayout.OnRefreshListener {
    public static final String ARGS_PAGE = "ydscience";
    private int currentPage = 1;
    int myPage = 0;
    @BindView(R.id.newsReyclerView) RecyclerViewEmptySupport mRecyclerView;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<NewDetailes> mNewInfosLists;
    private NewsInfoAdapter mNewsInfoAdapter;
    private NewDetailsPersenterImpl mNewDetailsPersenter;
    private WeChatNewsPresenterImpl mWeChatNewsPresenter;
    private ZhiHuInfoPresenterImpl mZhiHuInfoPresenter;
    private RxPermissions mRxPermissions;
    private boolean isRefresh = false;
    private LinearLayoutManager mLinearLayoutManager;
    private int currentDateIndex = 1;
    private boolean isPullLoading = true;
    public static PagerNewsFragment getIntance(int page){
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS_PAGE,page);
        PagerNewsFragment pagerNewsFragment = new PagerNewsFragment();
        pagerNewsFragment.setArguments(bundle);
        return pagerNewsFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_info,null);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);//设置menu回调
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        acquireInternetData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPage =  getArguments().getInt(ARGS_PAGE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void init(){
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE,Color.GREEN,Color.BLUE);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void acquireInternetData(){
        mRxPermissions = new RxPermissions(getActivity());
        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE
        ,Manifest.permission.ACCESS_NETWORK_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                            if(aBoolean){
                                configAdapterData();
                            }else {
                                ToastUtils.showToast(getActivity(),"权限拒绝后部分功能将无法使用");
                            }
                    }
                });
    }

    private void configAdapterData(){
        mNewInfosLists = new ArrayList<>();
        mNewDetailsPersenter = new NewDetailsPersenterImpl(this,getActivity());
        mWeChatNewsPresenter = new WeChatNewsPresenterImpl(this,getActivity());
        mZhiHuInfoPresenter = new ZhiHuInfoPresenterImpl(this,getActivity());
        requestInternetData();
        mNewsInfoAdapter =  new NewsInfoAdapter(getActivity(),mNewInfosLists);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mNewsInfoAdapter);
        mRecyclerView.setEmptyView(LayoutInflater.from(getActivity())
                .inflate(R.layout.layout_empty_view,null));
        mNewsInfoAdapter.setOnItemOnClickListener(new NewsInfoAdapter.OnItemOnClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mNewInfosLists != null && mNewInfosLists.size()>0){
                    Intent intent = new Intent(getActivity(), ScrollingActivity.class);
                    intent.putExtra(Commons.TRANSFER_URL,mNewInfosLists.get(position).getUrl());
                    intent.putExtra(Commons.TRANSFER_TITLE,mNewInfosLists.get(position).getTitle());
                    intent.putExtra(Commons.TRANSFER_IMAGE_URL,mNewInfosLists.get(position).getThumbnail_pic_s());
                    startActivity(intent);
                }else {
                    MyLog.print("mNewInfoSlist is null");
                }

            }
        });
        configDownRefresh();
    }

    private void requestInternetData(){
        if (isPullLoading){
            startLoadingDialog();
        }else{
            stopLoadingDialog();
        }
        avoidNoResponse();//避免长时间无响应，不能退出，影响用户体验。
            MyLog.print("当前页面为"+myPage);

        switch (myPage){
            case 0:
                mWeChatNewsPresenter.getWeChatNewsInfo(currentPage);
                break;
            case 1:
                if (Commons.isFristLoadData){
                    Commons.isFristLoadData  =  false;
                    MyLog.print("请求最新数据");
                    mZhiHuInfoPresenter.getLatestZhiHuNews();
                }else {
                    MyLog.print("请求数据日期"+currentDateIndex);
                    currentDateIndex --;
                    mZhiHuInfoPresenter.getOrderByTimeZhiHuNews(Utils.getCurrentDate(currentDateIndex));
                }
                break;
            case 2:
                mNewDetailsPersenter.getNewDetails(Commons.guonei);
                break;
            case 3:
                mNewDetailsPersenter.getNewDetails(Commons.guoji);
                break;
            case 4:
                mNewDetailsPersenter.getNewDetails(Commons.junshi);
                break;
            case 5:
                mNewDetailsPersenter.getNewDetails(Commons.keji);
                break;
            default:break;
        }
    }

    @Override
    public void updateNewInfo(ArrayList<NewDetailes> newDetailesList,int currentView) {
        stopLoadingDialog();//加载完成后取消隐藏刷新图标
        isRefresh = false;
        MyLog.print("当前的List为");
        if (newDetailesList.size() > 0){
            if (isPullLoading){
                mNewInfosLists.addAll(0,newDetailesList);
            }else {
                mNewInfosLists.addAll(newDetailesList);
            }
            removeDuplicate(mNewInfosLists);
            mNewsInfoAdapter.notifyItemRemoved(mNewsInfoAdapter.getItemCount());
        }else {
            ToastUtils.showToast(getActivity(),"消息为空");
        }

    }

    public void removeDuplicate(List<NewDetailes> list){
        for(int i=0;i<list.size()-1;i++){
            for(int j=list.size()-1;j>i;j--){
                if(list.get(j).getTitle().equals(list.get(i).getTitle())){
                    list.remove(j);
                }
            }
        }
        mNewsInfoAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadError(String error) {
        stopLoadingDialog();
        ToastUtils.showToast(getActivity(),error);
        mNewsInfoAdapter.notifyItemRemoved(mNewsInfoAdapter.getItemCount());
    }

    @Override
    public void onRefresh() {
        if (!isRefresh){
            isRefresh = true;//正在刷新
            isPullLoading = true;
            currentPage++;
            requestInternetData();
        }
    }

    private void configDownRefresh(){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
                isPullLoading = false;
                if (lastVisibleItemPosition + 1 == mNewsInfoAdapter.getItemCount()) {
                    boolean isRefreshing = mSwipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        mNewsInfoAdapter.notifyItemRemoved(mNewsInfoAdapter.getItemCount());
                        return;
                    }
                    if (!isRefresh) {
                        isRefresh = true;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentPage++;
                                requestInternetData();
                            }
                        },1000);

                    }
                }
            }
        });
    }
    private void avoidNoResponse()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                 stopLoadingDialog();
            }
        },20000);
    }
    private void startLoadingDialog(){
        if (mSwipeRefreshLayout != null && !mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    private void stopLoadingDialog(){
//        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
//        }
    }
}
