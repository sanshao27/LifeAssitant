package com.ydscience.lifeassistant.interfaces;

import java.util.List;

/**
 * Created by ydscience on 2017/6/7.
 */

public interface OnPermissionListener {
    void onGranted();//授权

    void onDenied(List<String> deniedPermissions);//为授权

}
