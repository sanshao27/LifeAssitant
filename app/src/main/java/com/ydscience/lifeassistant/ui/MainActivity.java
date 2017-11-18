package com.ydscience.lifeassistant.ui;
import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ydscience.lifeassistant.R;
import com.ydscience.lifeassistant.event.LocationEvent;
import com.ydscience.lifeassistant.event.RefreshEvent;
import com.ydscience.lifeassistant.ui.activity.BaseActivity;
import com.ydscience.lifeassistant.ui.fragment.ConvenientFragent;
import com.ydscience.lifeassistant.ui.fragment.NewFragment;
import com.ydscience.lifeassistant.ui.fragment.ProgramFragment;
import com.ydscience.lifeassistant.ui.fragment.SmileFragment;
import com.ydscience.lifeassistant.utils.BottomNavigationViewHelper;
import com.ydscience.lifeassistant.utils.Commons;
import com.ydscience.lifeassistant.utils.MyLog;
import com.ydscience.lifeassistant.utils.ToastUtils;
import com.ydscience.lifeassistant.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

//    @BindView(R.id.toolbar_main) Toolbar mToolbar;
    @BindView(R.id.navigation) BottomNavigationView navigation;
    @BindView(R.id.viewpager) ViewPager mViewPager;
    @BindArray(R.array.declaed_title_name) String[] titleArrays;
    private NewFragment mNewFragment;
    private ConvenientFragent mConvenientFragent;
    private SmileFragment mSmileFragment;
    private ProgramFragment mProgramFragment;
    private  MenuItem mMenuItem;
    private AMapLocationClient mAMapLocationClient = null;
    private AMapLocationClientOption mAMapLocationClientOption = null;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.navigation_new:
//                    mToolbar.setTitle(R.string.title_new);
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.navigation_life_help:
//                    mToolbar.setTitle(R.string.title_life_help);
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.navigation_smile:
//                    mToolbar.setTitle(R.string.title_smile);
                    mViewPager.setCurrentItem(2);
                    break;
                case R.id.navigation_program:
//                    mToolbar.setTitle(R.string.title_program);
                    mViewPager.setCurrentItem(3);
                    break;
                default:
//                    mToolbar.setTitle(R.string.title_new);
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        smoothSwitchScreen();
        setContentView(R.layout.activity_main);
        initView();
        initViewPager();
        requestLocationServer();
        MyLog.print("Activity Oncreate创建");
    }

    private void initView(){
        ButterKnife.bind(this);
//        setSupportActionBar(mToolbar);
//        mToolbar.setTitle(R.string.title_new);
//        mToolbar.setTitleTextColor(getResources().getColor(R.color.logoTextColor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            BottomNavigationViewHelper.disableShiftMode(navigation);
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initViewPager(){
        mViewPager.addOnPageChangeListener(this);
        mNewFragment = new NewFragment();
        mConvenientFragent = new ConvenientFragent();
        mSmileFragment = new SmileFragment();
        mProgramFragment = new ProgramFragment();
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
//                        mToolbar.setTitle(titleArrays[0]);
                        return mNewFragment;
                    case 1:
//                        mToolbar.setTitle(titleArrays[1]);
                        return mConvenientFragent;
                    case 2:
//                        mToolbar.setTitle(titleArrays[2]);
                        return mSmileFragment;
                    case 3:
//                        mToolbar.setTitle(titleArrays[3]);
                        return mProgramFragment;
                    default:break;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 4;
            }
        });
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (mMenuItem != null){
            mMenuItem.setChecked(true);
        }else {
            navigation.getMenu().getItem(0).setChecked(true);
        }
        //当页面滑动时，被选择的一项高亮
        navigation.getMenu().getItem(position).setChecked(true);
//        mToolbar.setTitle(titleArrays[position]);
        mMenuItem = navigation.getMenu().getItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destoryLocation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void smoothSwitchScreen(){
        ViewGroup rootView = ((ViewGroup) this.findViewById(android.R.id.content));
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        rootView.setPadding(0, statusBarHeight, 0, 0);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    private void requestLocationServer() {
        new RxPermissions(this).request(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean){
                            initLoaction();
                            startLocation();
                        }else {
                            ToastUtils.showToast(MainActivity.this,"权限拒绝后部分功能将无法使用");
                        }
                    }
                });
    }
    //实现类似于微信一样初次进入加载欢迎界面，后台运行后不再加载欢迎界面
//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        startActivity(intent);
//    }

    private void initLoaction(){
        mAMapLocationClient = new AMapLocationClient(getApplicationContext());
        mAMapLocationClientOption =new AMapLocationClientOption();
        mAMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mAMapLocationClientOption.setOnceLocationLatest(true);
        mAMapLocationClientOption.setNeedAddress(true);
        mAMapLocationClientOption.setMockEnable(true);
        mAMapLocationClientOption.setHttpTimeOut(10000);
        mAMapLocationClientOption.setLocationCacheEnable(false);
        mAMapLocationClient.setLocationOption(mAMapLocationClientOption);
        mAMapLocationClient.setLocationListener(mAMapLocationListener);
    }
    private AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                MyLog.print("定位结果" + aMapLocation.getErrorCode());
                int result ;
                if (aMapLocation.getErrorCode() == 0) {
                    result = 0;
                    Commons.LOCATION_RESULT = 0;
                    Commons.LOCATION_CITY = aMapLocation.getCity();
                    stopLocation();
                }else {
                    Commons.LOCATION_RESULT = 1;
                    result = 1;
                }
                LocationEvent event = new LocationEvent();
                event.setResult(result);
                event.setCity(aMapLocation.getCity());
                try {
                    Thread.sleep(1000);
                    EventBus.getDefault().postSticky(event);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private void startLocation(){
        mAMapLocationClient.startLocation();
    }

    private void stopLocation(){
        mAMapLocationClient.stopLocation();
    }

    private void destoryLocation(){
        if(mAMapLocationClient != null){
            mAMapLocationClient.onDestroy();
            mAMapLocationClient = null;
            mAMapLocationClientOption  = null;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RefreshEvent event){
        requestLocationServer();
    }
}
