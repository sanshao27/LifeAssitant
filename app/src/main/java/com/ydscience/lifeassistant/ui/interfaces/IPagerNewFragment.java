package com.ydscience.lifeassistant.ui.interfaces;

import com.ydscience.lifeassistant.bean.news.NewDetailes;
import com.ydscience.lifeassistant.bean.wechat.WeChatNews;

import java.util.ArrayList;

/**
 * Created by ydscience on 2017/6/28.
 */

public interface IPagerNewFragment {
    void updateNewInfo(ArrayList<NewDetailes> newDetailesList,int currentView);

    void loadError(String error);
}
