package com.ydscience.lifeassistant.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.ydscience.lifeassistant.R;
import com.ydscience.lifeassistant.bean.weather.WeatherCurrentInfo;
import com.ydscience.lifeassistant.bean.weather.WeatherMoreDetails;
import com.ydscience.lifeassistant.event.LocationEvent;
import com.ydscience.lifeassistant.event.RefreshEvent;
import com.ydscience.lifeassistant.presenter.WeatherFromDbPresenterImpl;
import com.ydscience.lifeassistant.presenter.WeatherInfoPresenterImpl;
import com.ydscience.lifeassistant.ui.activity.SelectMoreCityActivity;
import com.ydscience.lifeassistant.ui.interfaces.IWeatherInfo;
import com.ydscience.lifeassistant.utils.Commons;
import com.ydscience.lifeassistant.utils.MyLog;
import com.ydscience.lifeassistant.utils.ToastUtils;
import com.ydscience.lifeassistant.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ydscience on 2017/6/10.
 */

public class ConvenientFragent extends BaseFragment implements IWeatherInfo {

    @BindView(R.id.topTitleView) TextView mTopTitleView;
    @BindView(R.id.weatherIconImageView) ImageView mWeatherIconImageView;
    @BindView(R.id.tempatureDetailsView) TextView mTempatureDetailsView;
    @BindView(R.id.windPowerView) TextView mWindPowerView;
    @BindView(R.id.windRangeView) TextView mWindRangeView;
    @BindView(R.id.weatherDescribeView) TextView mWeatherDescribeView;
    @BindView(R.id.currentTemptyreView) TextView mCurrentTemptyreView;
    @BindView(R.id.AqiView) TextView mAqiView;
    @BindArray(R.array.weather_aqi) String[] weatherAqiArray;
    @BindView(R.id.linearView) LinearLayout mLinearView;
    @BindView(R.id.textView2) TextView mInternetErrorView;
    @BindView(R.id.imageButton) ImageButton mRefreshImageBtn;
    @BindView(R.id.moreInfoBtn) ImageButton mMoreInfoBtn;
    @BindView(R.id.toolbar_weather) Toolbar weatherToolbar;
    private String topTitleInfo;
    private WeatherInfoPresenterImpl mWeatherInfoPresenter;
    private WeatherFromDbPresenterImpl mWeatherFromDbPresenter;
    private HashMap<String, String> maps;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_frament, null);
        ButterKnife.bind(this, view);
        mMoreInfoBtn.setVisibility(View.INVISIBLE);
        initTolbarListener();
        return view;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        initWeatherIconMap();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mWeatherInfoPresenter = new WeatherInfoPresenterImpl(this, getActivity());
        mWeatherFromDbPresenter = new WeatherFromDbPresenterImpl(getActivity(), this);
        nextRequestInternet();
        getOfflineDataFromDb();//首先获取本地数据库数据，等定位成功之后更新为新的数据
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initTolbarListener(){
        if (weatherToolbar != null){
            weatherToolbar.inflateMenu(R.menu.menu_weather);
            weatherToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.change_city:
                            startActivity(new Intent(getActivity(), SelectMoreCityActivity.class));
                            break;
                        default:break;
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public void setTodayWeather(WeatherCurrentInfo todayWeather) {
        topTitleInfo = todayWeather.getCity();
        if (weatherAqiArray != null && weatherAqiArray.length != 0) {
            int aqi = Integer.parseInt(todayWeather.getAqi());
            if (aqi > 0 && aqi <= 50) {
                mAqiView.setText("AQI:" + aqi + "  " + weatherAqiArray[0]);
            } else if (aqi > 50 && aqi <= 100) {
                mAqiView.setText("AQI:" + aqi + "  " + weatherAqiArray[1]);
            } else if (aqi > 100 && aqi <= 150) {
                mAqiView.setText("AQI:" + aqi + "  " + weatherAqiArray[2]);
            } else if (aqi > 150 && aqi <= 200) {
                mAqiView.setText("AQI:" + aqi + "  " + weatherAqiArray[3]);
            } else if (aqi > 200 && aqi <= 300) {
                mAqiView.setText("AQI:" + aqi + "  " + weatherAqiArray[4]);
            } else if (aqi > 300) {
                mAqiView.setText("AQI:" + aqi + "  " + weatherAqiArray[5]);
            }
            mCurrentTemptyreView.setText("当前温度为:" + todayWeather.getWendu() + "°");
            hideInternetAskResult();
        }
    }

    @Override
    public void setMoreWeather(List<WeatherMoreDetails> moreDetailsList) {
        if (moreDetailsList.size() > 0) {
            topTitleInfo = topTitleInfo + "  " + moreDetailsList.get(0).getDate();
            mTopTitleView.setText(topTitleInfo);
            mTempatureDetailsView.setText(moreDetailsList.get(0).getHigh().substring(3, 6) + "/" +
                    moreDetailsList.get(0).getLow().substring(3, 6));
            mWindPowerView.setText(moreDetailsList.get(0).getFengxiang());
            mWindRangeView.setText("风力" + moreDetailsList.get(0).getFengli().substring(9, 11)+"级");
            String type = moreDetailsList.get(0).getType();
            mWeatherDescribeView.setText(type);
            if (maps.containsKey(type)) {
                int id = getResources().getIdentifier(maps.get(type), "drawable", "com.ydscience.lifeassistant");
                mWeatherIconImageView.setImageResource(id);
            } else {
                mWeatherIconImageView.setImageResource(R.drawable.ic_sunny);
            }
            mLinearView.removeAllViews();
            for (int i = 0; i < moreDetailsList.size(); i++) {
                if (i > 0) {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_weather, null);
                    TextView dateTV = (TextView) view.findViewById(R.id.date);
                    ImageView todayWeatherImage = (ImageView) view.findViewById(R.id.weatherImage);
                    TextView todayTemperatureTV = (TextView) view.findViewById(R.id.weatherTemp);
                    TextView todayWindTV = (TextView) view.findViewById(R.id.wind);
                    TextView todayWeatherTV = (TextView) view.findViewById(R.id.weather);
                    dateTV.setText(moreDetailsList.get(i).getDate());
                    todayTemperatureTV.setText(moreDetailsList.get(i).getHigh().substring(3, 6) + "/"
                            + moreDetailsList.get(i).getLow().substring(3, 6));
                    todayWindTV.setText(moreDetailsList.get(i).getFengxiang());
                    String itemType = moreDetailsList.get(i).getType();
                    if (maps.containsKey(itemType)) {
                        int itemId = getResources().getIdentifier(maps.get(itemType), "drawable", "com.ydscience.lifeassistant");
                        todayWeatherImage.setImageResource(itemId);
                    } else {
                        todayWeatherImage.setImageResource(R.drawable.ic_sunny);
                    }
                    todayWeatherTV.setText(itemType);
                    mLinearView.addView(view);
                }
            }
            hideInternetAskResult();
        }
    }

    @Override
    public void loadError(String errorInfo) {
        ToastUtils.showToast(getActivity(), errorInfo);
        mInternetErrorView.setText(getString(R.string.refresh_message));
    }

    private void initWeatherIconMap() {
        maps = new HashMap<>();
        maps.put("晴", "ic_sunny");
        maps.put("晴转多云", "ic_sunny_to_cloudy");
        maps.put("晴转阴", "ic_sunny_to_cloudy");
        maps.put("多云转晴", "ic_cloudy_to_sunny");
        maps.put("阴转晴", "ic_cloudy_to_sunny");
        maps.put("多云", "ic_cloudy");
        maps.put("阴", "ic_cloudy");
        maps.put("小雨", "ic_light_rain");
        maps.put("小到中雨", "ic_middle_rain");
        maps.put("中雨", "ic_middle_rain");
        maps.put("大雨", "ic_big_rain");
        maps.put("阵雨", "ic_big_rain");
        maps.put("中到大雨", "ic_big_rain");
        maps.put("暴雨", "ic_rainstorm");
        maps.put("雷阵雨", "ic_thunderstorms");
        maps.put("小雪", "ic_small_snow");
        maps.put("中雪", "ic_middle_snow");
        maps.put("大雪", "ic_big_snow");
        maps.put("阵雪", "ic_big_snow");
        maps.put("暴雪", "ic_blizzard");
        maps.put("雨夹雪", "ic_rain_plus_snow");
        maps.put("大雾", "ic_fog");
        maps.put("霾", "ic_mai");
    }

    private void nextRequestInternet(){
        ImageButton refreshBtn = (ImageButton) getActivity().findViewById(R.id.imageButton);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInternetErrorView.setText(getString(R.string.refreshing));
                EventBus.getDefault().post(new RefreshEvent());
            }
        });
        }

    private void showInternetAskResult(){
        mInternetErrorView.setVisibility(View.VISIBLE);
        mRefreshImageBtn.setVisibility(View.VISIBLE);
    }

    private void hideInternetAskResult(){
        mInternetErrorView.setVisibility(View.INVISIBLE);
        mRefreshImageBtn.setVisibility(View.INVISIBLE);
    }

    private void getOfflineDataFromDb(){
        if(mWeatherFromDbPresenter != null){
            mWeatherFromDbPresenter.QueryWeatherInfoFromDb();
            showInternetAskResult();
        }
    }
    @Subscribe
    public void onMessageEvent(LocationEvent event){
        if (event.getResult() == 0){
            hideInternetAskResult();
            mWeatherInfoPresenter.getWeatherInfo(event.getCity());
        }else if (event.getResult() == 1){
            mInternetErrorView.setText(getString(R.string.refresh_message));
            getOfflineDataFromDb();
        }
    }



}
