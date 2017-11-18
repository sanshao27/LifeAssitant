package com.ydscience.lifeassistant.ui.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.ydscience.lifeassistant.interfaces.OnPermissionListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ydscience on 2017/6/7.
 */

public class BaseActivity extends AppCompatActivity {
    //当在Activity中使用权限时，则使用自己基于接口回调的权限处理，如何不再则使用第三方框架PermissionDispatcher
    private OnPermissionListener mOnPermissionListener;
    private   final int PERMISSION_REQUESTCODE = 1;

    public void requestPermission(String[] permissions, OnPermissionListener listener){
        mOnPermissionListener = listener;
        List<String> permissionLists = new ArrayList<>();
        for (String permission: permissions){
            if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                permissionLists.add(permission);
            }
        }
        if (!permissionLists.isEmpty()){//权限没有授予
            ActivityCompat.requestPermissions(this,permissionLists.toArray(new String[permissionLists.size()])
                    ,PERMISSION_REQUESTCODE);
        }else {
            mOnPermissionListener.onGranted();//权限全部授予
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0){
            List<String> deniedPermissionLists = new ArrayList<>();
            for(int i = 0;i < grantResults.length;i++){
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                    deniedPermissionLists.add(permissions[i]);
                }
            }
            if (!deniedPermissionLists.isEmpty()){
                mOnPermissionListener.onDenied(deniedPermissionLists);
            }else {
                mOnPermissionListener.onGranted();
            }
        }
    }

}