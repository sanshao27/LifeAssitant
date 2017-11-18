package com.ydscience.lifeassistant.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.example.swipebackactivity.SwipeBackLayout;
import com.example.swipebackactivity.app.SwipeBackActivity;

/**
 * Created by ydscience on 2017/7/8.
 */

public class BaseSwipeBackActivity extends SwipeBackActivity {

    private SwipeBackLayout mSwipeBackLayout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setSwipeBackEnable(true);
        mSwipeBackLayout = getSwipeBackLayout();
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        // 滑动退出的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法
//        mSwipeBackLayout.setEdgeSize(300);

    }
}
