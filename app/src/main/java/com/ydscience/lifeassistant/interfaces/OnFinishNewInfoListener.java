package com.ydscience.lifeassistant.interfaces;

import com.ydscience.lifeassistant.bean.news.NewDetailes;

import java.util.ArrayList;

/**
 * Created by ydscience on 2017/6/28.
 */

public interface OnFinishNewInfoListener {
    void loadSuccess(ArrayList<NewDetailes> newResult);
    void loadFailed(String errorInfo);
}
