package com.ydscience.lifeassistant.interfaces;

import com.ydscience.lifeassistant.bean.zhihu.ZhiHuStory;

import java.util.ArrayList;

/**
 * Created by ydscience on 2017/7/8.
 */

public interface OnFinishUrlLoadListener {
    void loadSuccess(ZhiHuStory  zhiHuStory);
    void loadFailed(String errorInfo);
}
