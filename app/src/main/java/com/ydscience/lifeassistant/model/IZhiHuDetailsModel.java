package com.ydscience.lifeassistant.model;

import android.content.Context;

import com.ydscience.lifeassistant.interfaces.OnFinishNewInfoListener;

/**
 * Created by ydscience on 2017/7/6.
 */

public interface IZhiHuDetailsModel {
    void getLatestNews(OnFinishNewInfoListener listener, Context context);
    void getOrderByDateNews(String date,OnFinishNewInfoListener listener,Context context);
}
