package com.ydscience.lifeassistant.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ydscience on 2017/6/28.
 */

public class ToastUtils {
    public static Toast mToast;

    public static  void showToast(Context context,String message){
        if (mToast == null){
            mToast = Toast.makeText(context,message,Toast.LENGTH_SHORT);
        }else {
            mToast.setText(message);
        }
        mToast.show();
    }

}
