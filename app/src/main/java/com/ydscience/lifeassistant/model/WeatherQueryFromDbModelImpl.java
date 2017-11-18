package com.ydscience.lifeassistant.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.SparseArray;

import com.ydscience.lifeassistant.bean.weather.WeatherCurrentInfo;
import com.ydscience.lifeassistant.bean.weather.WeatherMoreDetails;
import com.ydscience.lifeassistant.db.DbHelper;
import com.ydscience.lifeassistant.interfaces.OnFinishWeatherInfoListener;
import com.ydscience.lifeassistant.utils.MyLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ydscience on 2017/9/10.
 */

public class WeatherQueryFromDbModelImpl implements IWeatherQueryFromDbModel {
    @Override
    public void queryWeather(OnFinishWeatherInfoListener listener, Context context) {
        Uri todayUri = Uri.parse("content://com.ydscience.lifeassistant.db.WeatherInfoProvider/curtoday");
        Uri moreUri  = Uri.parse("content://com.ydscience.lifeassistant.db.WeatherInfoProvider/moreinfo");
        Cursor cursor = context.getContentResolver().query(todayUri,null,null,null,null);
        List<WeatherMoreDetails> moreDetailsArrayList = new ArrayList<>();
        if(cursor != null && cursor.moveToNext()){
            WeatherCurrentInfo currentInfo = new WeatherCurrentInfo();
            currentInfo.setAqi(cursor.getString(cursor.getColumnIndex(DbHelper.Entry.AQI)));
            currentInfo.setCity(cursor.getString(cursor.getColumnIndex(DbHelper.Entry.CITY)));
            currentInfo.setWendu(cursor.getString(cursor.getColumnIndex(DbHelper.Entry.WENDU)));
            WeatherMoreDetails  moreDetails = new WeatherMoreDetails();
            moreDetails.setDate(cursor.getString(cursor.getColumnIndex(DbHelper.Entry.DATE)));
            moreDetails.setHigh(cursor.getString(cursor.getColumnIndex(DbHelper.Entry.HIGH)));
            moreDetails.setLow(cursor.getString(cursor.getColumnIndex(DbHelper.Entry.LOW)));
            moreDetails.setFengli(cursor.getString(cursor.getColumnIndex(DbHelper.Entry.FENGLI)));
            moreDetails.setFengxiang(cursor.getString(cursor.getColumnIndex(DbHelper.Entry.FENGXIANG)));
            moreDetails.setType(cursor.getString(cursor.getColumnIndex(DbHelper.Entry.TYPE)));
            listener.getToadyWeather(currentInfo);
            moreDetailsArrayList.add(moreDetails);
        }
        cursor.close();
        Cursor moreInfoCursor = context.getContentResolver().query(moreUri,null,null,null,null);
        if(moreInfoCursor != null){
            while (moreInfoCursor.moveToNext()){
                WeatherMoreDetails  moreDetails = new WeatherMoreDetails();
                moreDetails.setDate(moreInfoCursor.getString(moreInfoCursor.getColumnIndex(DbHelper.Entry.DATE)));
                moreDetails.setHigh(moreInfoCursor.getString(moreInfoCursor.getColumnIndex(DbHelper.Entry.HIGH)));
                moreDetails.setLow(moreInfoCursor.getString(moreInfoCursor.getColumnIndex(DbHelper.Entry.LOW)));
                moreDetails.setFengli(moreInfoCursor.getString(moreInfoCursor.getColumnIndex(DbHelper.Entry.FENGLI)));
                moreDetails.setFengxiang(moreInfoCursor.getString(moreInfoCursor.getColumnIndex(DbHelper.Entry.FENGXIANG)));
                moreDetails.setType(moreInfoCursor.getString(moreInfoCursor.getColumnIndex(DbHelper.Entry.TYPE)));
                moreDetailsArrayList.add(moreDetails);
            }
            MyLog.print("查询数据大小为"+moreDetailsArrayList.size());
            listener.getMoreWeatherInfo(moreDetailsArrayList);
        }
        moreInfoCursor.close();
    }


}
