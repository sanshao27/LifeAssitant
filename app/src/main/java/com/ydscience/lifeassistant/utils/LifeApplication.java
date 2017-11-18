package com.ydscience.lifeassistant.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by ydscience on 2017/6/6.
 */

public class LifeApplication extends Application {
    static {
        System.loadLibrary("native-lib");
    }
    public static LifeApplication mLifeApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        String md5Info = Utils.getMD5(Utils.getSignature(this));
        MyLog.print("MD5值为"+md5Info+"hash结果"+check(md5Info));
        if (!check(md5Info)){
          //  android.os.Process.killProcess(android.os.Process.myPid());
            //卸载此应用，但是会弹出框让用户选择
//            Uri packageUri =  Uri.parse("package:"+MainActivity.this.getPackageName());
//            startActivity(new Intent(Intent.ACTION_DELETE,packageUri));
        }else {
            MyLog.print("MD5值为"+md5Info);
        }
        mLifeApplication = this;
    }

    public static Context getContext(){
            return mLifeApplication;
    }
    public native boolean check(String signMsg);
}
