package com.ydscience.lifeassistant.interfaces;

import com.ydscience.lifeassistant.bean.weather.WeatherCurrentInfo;
import com.ydscience.lifeassistant.bean.weather.WeatherMoreDetails;

import java.util.List;

/**
 * Created by ydscience on 2017/8/24.
 */

public interface OnFinishWeatherInfoListener {
    void getToadyWeather(WeatherCurrentInfo todayInfo);
    void getMoreWeatherInfo(List<WeatherMoreDetails> moreDetailsList);
    void getError(String errorInfo);
}
