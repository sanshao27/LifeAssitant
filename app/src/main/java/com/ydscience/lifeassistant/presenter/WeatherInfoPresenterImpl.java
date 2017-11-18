package com.ydscience.lifeassistant.presenter;

import android.content.Context;

import com.ydscience.lifeassistant.bean.weather.WeatherCurrentInfo;
import com.ydscience.lifeassistant.bean.weather.WeatherMoreDetails;
import com.ydscience.lifeassistant.interfaces.OnFinishWeatherInfoListener;
import com.ydscience.lifeassistant.model.IWeatherInfoModel;
import com.ydscience.lifeassistant.model.WeatherInfoModelImpl;
import com.ydscience.lifeassistant.ui.interfaces.IWeatherInfo;

import java.util.List;

/**
 * Created by ydscience on 2017/8/24.
 */

public class WeatherInfoPresenterImpl implements IWeatherInfoPresenter,OnFinishWeatherInfoListener {
    private IWeatherInfo mIWeatherInfo;
    private Context mContext;
    private WeatherInfoModelImpl mIWeatherInfoModel;

    public WeatherInfoPresenterImpl(IWeatherInfo info, Context context) {
        mIWeatherInfo = info;
        mContext = context;
        this.mIWeatherInfoModel = new WeatherInfoModelImpl(mContext);
    }

    @Override
    public void getWeatherInfo(String city) {
        mIWeatherInfoModel.getInfo(city,this);
    }

    @Override
    public void getToadyWeather(WeatherCurrentInfo todayInfo) {
        mIWeatherInfo.setTodayWeather(todayInfo);
    }

    @Override
    public void getMoreWeatherInfo(List<WeatherMoreDetails> moreDetailsList) {
        mIWeatherInfo.setMoreWeather(moreDetailsList);
    }

    @Override
    public void getError(String errorInfo) {
        mIWeatherInfo.loadError(errorInfo);
    }
}
