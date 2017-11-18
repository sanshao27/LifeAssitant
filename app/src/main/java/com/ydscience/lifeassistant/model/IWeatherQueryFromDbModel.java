package com.ydscience.lifeassistant.model;

import android.content.Context;

import com.ydscience.lifeassistant.interfaces.OnFinishWeatherInfoListener;

/**
 * Created by ydscience on 2017/9/10.
 */

public interface IWeatherQueryFromDbModel {
    void queryWeather(OnFinishWeatherInfoListener listener, Context context);
}
