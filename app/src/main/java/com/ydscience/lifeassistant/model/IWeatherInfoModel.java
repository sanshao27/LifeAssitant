package com.ydscience.lifeassistant.model;

import com.ydscience.lifeassistant.interfaces.OnFinishWeatherInfoListener;

/**
 * Created by ydscience on 2017/8/24.
 */

public interface IWeatherInfoModel {
    void getInfo(String city, OnFinishWeatherInfoListener listener);
}
