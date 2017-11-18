package com.ydscience.lifeassistant.presenter;

import android.content.Context;

import com.ydscience.lifeassistant.bean.weather.WeatherCurrentInfo;
import com.ydscience.lifeassistant.bean.weather.WeatherMoreDetails;
import com.ydscience.lifeassistant.interfaces.OnFinishWeatherInfoListener;
import com.ydscience.lifeassistant.model.WeatherQueryFromDbModelImpl;
import com.ydscience.lifeassistant.ui.interfaces.IWeatherInfo;

import java.util.List;

/**
 * Created by ydscience on 2017/9/10.
 */

public class WeatherFromDbPresenterImpl implements IWeatherFromDbPresenter,OnFinishWeatherInfoListener{
    private IWeatherInfo mIWeatherInfo;
    private Context mContext;
    private WeatherQueryFromDbModelImpl mWeatherQueryFromDbModel;
    public WeatherFromDbPresenterImpl(Context context,IWeatherInfo info){
        this.mContext = context;
        this.mIWeatherInfo = info;
        this.mWeatherQueryFromDbModel = new WeatherQueryFromDbModelImpl();
    }
    @Override
    public void QueryWeatherInfoFromDb() {
        mWeatherQueryFromDbModel.queryWeather(this,mContext);
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
