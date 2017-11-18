package com.ydscience.lifeassistant.model;

import android.content.Context;

import com.ydscience.lifeassistant.interfaces.OnFinishNewInfoListener;

/**
 * Created by ydscience on 2017/6/28.
 */

public interface INewDetailsModel {
    void getNewDetails(Context context,String newType, OnFinishNewInfoListener listener);
}
