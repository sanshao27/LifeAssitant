package com.ydscience.lifeassistant.utils;

import android.util.Log;


public class MyLog {
    public static void print(String info){
        Log.i("TAG",info);
    }

    public static void i(int info){
        String str = String.valueOf(info);
        Log.i("TAG","field_type"+str);
    }
    public static void d(int info){
        String str = String.valueOf(info);
        Log.i("TAG","field_status"+str);
    }

    public static void x(String info){
        Log.i("TAG","field_talker"+info);
    }
    public static void b(int info){
        String str = String.valueOf(info);
        Log.i("TAG","field_isSend"+str);
    }
    public static void y(String info){
        Log.i("TAG","field_content"+info);
    }
    public static void z(String info){
        Log.i("TAG","sendertitle"+info);
    }

}
