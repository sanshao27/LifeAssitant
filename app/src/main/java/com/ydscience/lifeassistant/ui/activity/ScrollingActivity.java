package com.ydscience.lifeassistant.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.swipebackactivity.app.SwipeBackActivity;
import com.ydscience.lifeassistant.R;
import com.ydscience.lifeassistant.bean.zhihu.ZhiHuStory;
import com.ydscience.lifeassistant.presenter.ZhiHuLoadUrlPresenterImpl;
import com.ydscience.lifeassistant.ui.interfaces.IZhiHuLoadUrlView;
import com.ydscience.lifeassistant.utils.Commons;
import com.ydscience.lifeassistant.utils.MyLog;
import com.ydscience.lifeassistant.utils.Utils;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScrollingActivity extends BaseSwipeBackActivity implements IZhiHuLoadUrlView{

    @BindView(R.id.imageView_New_fab) ImageView mImageViewNewFab;
    @BindView(R.id.toolbar_new) Toolbar mToolbarNew;
    @BindView(R.id.toolbar_layout) CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar) AppBarLayout mAppBar;
    @BindView(R.id.webView_show_new) WebView mWebView;
    @BindView(R.id.view_nestedScroll) NestedScrollView mNestedScrollView;
    @BindView(R.id.webView_progress)
    ProgressBar mProgressBar;
    private View mErrorView;
    private LinearLayout mWebViewParentView;
    private ZhiHuLoadUrlPresenterImpl mZhiHuLoadUrlPresenter;
    private String shareUrl,shareTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null){
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")){
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible",Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu,true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_share:
                loadShareUrl();
                break;
            default:break;
        }
        return true;
    }

    private  void init(){
        shareTitle = getIntent().getStringExtra(Commons.TRANSFER_TITLE);
        mToolbarNew.setTitle(shareTitle);
        setSupportActionBar(mToolbarNew);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initErrorPage();
        mZhiHuLoadUrlPresenter = new ZhiHuLoadUrlPresenterImpl(ScrollingActivity.this,this);
        mToolbarNew.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String url = getIntent().getStringExtra(Commons.TRANSFER_URL);
        shareUrl = url;
        MyLog.print("请求的URL为"+url);
        String imageUrl = getIntent().getStringExtra(Commons.TRANSFER_IMAGE_URL);
        Glide.with(this).load(imageUrl).into(mImageViewNewFab);
        mWebViewParentView = (LinearLayout) mWebView.getParent();
        configWebView(url);

    }

    private void configWebView(String loadUrl){
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);//支持缩放
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);//设置数据缓本地数据库
        settings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webViewCache");
        settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        //设置网页布局类型为适应屏幕，内容将自动缩放
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        loadURL(loadUrl);
        showUrlLoadErrorView();
    }

    private void loadURL(String url){
        if (TextUtils.isEmpty(url)){
            showErrorPage();
        }else {
            if (Utils.isNumber(url)){//验证传送URL如何是网址则直接加载，如果是ID，则根据ID获取URL
                MyLog.print("URL为数字");
                mZhiHuLoadUrlPresenter.getUrl(url,ScrollingActivity.this);
            }else {
                MyLog.print("URL为网址");
                if (mWebView != null){
                    mWebView.loadUrl(url);
                }
            }
        }

    }

    private void showUrlLoadErrorView(){
        mWebView.setWebViewClient(new WebViewClient()
        {
            //6.0以下执行
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                showErrorPage();
            }
            //6.0以上执行
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                showErrorPage();
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (mProgressBar != null){
                    if (newProgress == 100){
                        mProgressBar.setVisibility(View.GONE);
                    }else {
                        if (mProgressBar.getVisibility() == View.GONE){
                            mProgressBar.setVisibility(View.VISIBLE);
                        }
                        mProgressBar.setProgress(newProgress);
                    }
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    private void initErrorPage(){
        if (mErrorView == null){
            mErrorView = LayoutInflater.from(this).inflate(R.layout.url_loading_error,null);
            final TextView requestView = (TextView) mErrorView.findViewById(R.id.ask_request_view);
            requestView.setClickable(true);
            requestView.setFocusable(true);
            requestView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mWebView != null)
                        mWebView.reload();
                }
            });
        }
    }

    private void showErrorPage(){
        mWebViewParentView.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mWebViewParentView.addView(mErrorView,0,params);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void loadSuccess(ZhiHuStory zhiHuStory) {
        MyLog.print("URL"+zhiHuStory.getShareUrl()+" "+zhiHuStory.getImage());
        if (mWebView != null){
            if (!TextUtils.isEmpty(zhiHuStory.getBody())){
                mWebView.loadUrl(zhiHuStory.getShareUrl());
            }else {
                String data = Utils.buildHtmlWithCss(zhiHuStory.getBody(), zhiHuStory.getCss(), false);
                mWebView.loadDataWithBaseURL("file:///android_asset/",data,"text/html","utf-8","http//:daily.zhihu.com/");
            }
        }

    }

    @Override
    public void loadFailed(String errorInfo) {
        MyLog.print("加载失败");
    }

    private void loadShareUrl()
    {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT,shareTitle+" "+shareUrl+getString(R.string.share_logo));
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent,getString(R.string.share_title)));
    }
}
