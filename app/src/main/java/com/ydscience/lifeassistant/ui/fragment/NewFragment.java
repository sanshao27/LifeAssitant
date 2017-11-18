package com.ydscience.lifeassistant.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ydscience.lifeassistant.R;
import com.ydscience.lifeassistant.ui.adapter.NewsPagerAdapter;
import com.ydscience.lifeassistant.utils.Commons;
import com.ydscience.lifeassistant.utils.MyLog;
import com.ydscience.lifeassistant.utils.ScaleTransitionPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ydscience on 2017/6/10.
 */

public class NewFragment extends BaseFragment{
    private NewsPagerAdapter mNewsPagerAdapter;
    private List<String> mFragmentTitleLists;
    @BindView(R.id.view_pager_news) ViewPager mViewPager;
    @BindView(R.id.magic_indicator) MagicIndicator mMagicIndicator;
    @BindView(R.id.toolbar_main) Toolbar mToolbar;
    @BindArray(R.array.new_show_title) String[] titleStr;
    private int mCurrentTab = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_details,null);
        ButterKnife.bind(this,view);
        initToolBarListener();
        return view;
    }

    private void initToolBarListener(){
        if (mToolbar != null){
            mToolbar.inflateMenu(R.menu.menu_new);
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.setting:
                            MyLog.print("设置");
                            break;
                        default:break;
                    }
                    return true;
                }
            });
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFragmentTitleLists = Arrays.asList(titleStr);
        mNewsPagerAdapter  = new NewsPagerAdapter(getChildFragmentManager(),mFragmentTitleLists,initFragmentList());
        mViewPager.setAdapter(mNewsPagerAdapter);
        mViewPager.setCurrentItem(mCurrentTab);
        initMagicIncitor();
    }

    private List<Fragment> initFragmentList(){
        List<Fragment> lists = new ArrayList<>();
        for(int i=0;i<titleStr.length;i++){
            lists.add(PagerNewsFragment.getIntance(i));
        }
        return lists;
    }

    private void initMagicIncitor(){
        mMagicIndicator.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mFragmentTitleLists == null ? 0 : mFragmentTitleLists.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mFragmentTitleLists.get(index));
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.pagereCommonColor));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.pagerSelectedColor));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                BezierPagerIndicator indicator = new BezierPagerIndicator(context);
                indicator.setColors(Color.parseColor("#ff4a42"), Color.parseColor("#fcde64"), Color.parseColor("#73e8f4"), Color.parseColor("#76b0ff"), Color.parseColor("#c683fe"));
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }
}
