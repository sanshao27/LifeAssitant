package com.ydscience.lifeassistant.ui.interfaces;

import com.ydscience.lifeassistant.bean.weather.WeatherCurrentInfo;
import com.ydscience.lifeassistant.bean.weather.WeatherMoreDetails;

import java.util.List;

/**
 * Created by ydscience on 2017/8/24.
 */

public interface IWeatherInfo {

    void setTodayWeather(WeatherCurrentInfo todayWeather);

    void setMoreWeather(List<WeatherMoreDetails> moreDetailsList);

    void loadError(String errorInfo);
}
