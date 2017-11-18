package com.ydscience.lifeassistant.ui.interfaces;

import com.ydscience.lifeassistant.bean.zhihu.ZhiHuStory;

/**
 * Created by ydscience on 2017/7/8.
 */

public interface IZhiHuLoadUrlView {
    void loadSuccess(ZhiHuStory zhiHuStory);
    void loadFailed(String errorInfo);
}
