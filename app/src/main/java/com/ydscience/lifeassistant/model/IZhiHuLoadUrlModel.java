package com.ydscience.lifeassistant.model;

import android.content.Context;

import com.ydscience.lifeassistant.interfaces.OnFinishUrlLoadListener;

/**
 * Created by ydscience on 2017/7/8.
 */

public interface IZhiHuLoadUrlModel {
    void getUrl(String id, OnFinishUrlLoadListener listener, Context context);
}
