package com.ydscience.lifeassistant.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ydscience.lifeassistant.api.weather.WeatherInfoRequest;
import com.ydscience.lifeassistant.bean.weather.WeatherCurrentInfo;
import com.ydscience.lifeassistant.bean.weather.WeatherMoreDetails;
import com.ydscience.lifeassistant.bean.weather.WeatherState;
import com.ydscience.lifeassistant.db.DbHelper;
import com.ydscience.lifeassistant.interfaces.OnFinishWeatherInfoListener;
import com.ydscience.lifeassistant.utils.MyLog;
import com.ydscience.lifeassistant.utils.ToastUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ydscience on 2017/8/24.
 */

public class WeatherInfoModelImpl implements IWeatherInfoModel {
    private Context mContext;
    WeatherCurrentInfo  weatherCurrentInfo;
    public  WeatherInfoModelImpl(Context context){
        this.mContext = context;
    }
    @Override
    public void getInfo(String city, final OnFinishWeatherInfoListener listener) {

        WeatherInfoRequest.getWeatherApi().getWeatherInfo(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<WeatherState, WeatherCurrentInfo>() {
                    @Override
                    public WeatherCurrentInfo apply(@NonNull WeatherState weatherState) throws Exception {
                        if(weatherState.getStatus() == 1000 && weatherState.getDesc().equals("OK")){
                            return weatherState.getData();
                        }else {
                            listener.getError("加载失败");
                        }
                        return null;
                    }
                })
                .map(new Function<WeatherCurrentInfo, List<WeatherMoreDetails>>() {
                    @Override
                    public List<WeatherMoreDetails> apply(@NonNull WeatherCurrentInfo currentInfo) throws Exception {
                        if(currentInfo != null){
                            weatherCurrentInfo = currentInfo;
                            return currentInfo.getForecast();
                        }else {
                            listener.getError("加载失败");
                        }
                        return null;
                    }
                }).subscribe(new Observer<List<WeatherMoreDetails>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull List<WeatherMoreDetails> moreDetailsList) {
                if(moreDetailsList != null){
                    if(moreDetailsList.size() > 0){
                        listener.getToadyWeather(weatherCurrentInfo);
                        listener.getMoreWeatherInfo(moreDetailsList);
                        insertDate(mContext,weatherCurrentInfo,moreDetailsList);
                    }else {
                        listener.getError("加载失败");
                    }
                }else {
                    listener.getError("加载失败");
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                listener.getError("加载失败");
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void insertDate(final Context context, final WeatherCurrentInfo currentInfo, final List<WeatherMoreDetails> weatherMoreDetailsList){
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                e.onNext(updateWeather(context,currentInfo,weatherMoreDetailsList));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        if (!aBoolean){
                            ToastUtils.showToast(context,"数据库更新失败"+aBoolean);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public boolean updateWeather(Context context,WeatherCurrentInfo currentInfo,List<WeatherMoreDetails> weatherMoreDetailsList){
       try{
            SQLiteDatabase mSqliteDateBase = new DbHelper(context).getWritableDatabase();
            if(weatherMoreDetailsList.size() > 0){
                mSqliteDateBase.delete(DbHelper.Entry.TABLE_CUR_TODAY,null,null);
                mSqliteDateBase.delete(DbHelper.Entry.TABLE_MORE_INFO,null,null);
                ContentValues contentValues = new ContentValues();
                contentValues.put(DbHelper.Entry.CITY,currentInfo.getCity());
                contentValues.put(DbHelper.Entry.AQI,currentInfo.getAqi());
                contentValues.put(DbHelper.Entry.WENDU,currentInfo.getWendu());
                contentValues.put(DbHelper.Entry.DATE,weatherMoreDetailsList.get(0).getDate());
                contentValues.put(DbHelper.Entry.HIGH,weatherMoreDetailsList.get(0).getHigh());
                contentValues.put(DbHelper.Entry.LOW,weatherMoreDetailsList.get(0).getLow());
                contentValues.put(DbHelper.Entry.FENGLI,weatherMoreDetailsList.get(0).getFengli());
                contentValues.put(DbHelper.Entry.FENGXIANG,weatherMoreDetailsList.get(0).getFengxiang());
                contentValues.put(DbHelper.Entry.TYPE,weatherMoreDetailsList.get(0).getType());
                 mSqliteDateBase.insertWithOnConflict(DbHelper.Entry.TABLE_CUR_TODAY,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
                for(int i=1;i<weatherMoreDetailsList.size();i++){
                    ContentValues contentValuesMoreInfo = new ContentValues();
                    contentValuesMoreInfo.put(DbHelper.Entry.DATE,weatherMoreDetailsList.get(i).getDate());
                    contentValuesMoreInfo.put(DbHelper.Entry.HIGH,weatherMoreDetailsList.get(i).getHigh());
                    contentValuesMoreInfo.put(DbHelper.Entry.LOW,weatherMoreDetailsList.get(i).getLow());
                    contentValuesMoreInfo.put(DbHelper.Entry.FENGLI,weatherMoreDetailsList.get(i).getFengli());
                    contentValuesMoreInfo.put(DbHelper.Entry.FENGXIANG,weatherMoreDetailsList.get(i).getFengxiang());
                    contentValuesMoreInfo.put(DbHelper.Entry.TYPE,weatherMoreDetailsList.get(i).getType());
                    mSqliteDateBase.insertWithOnConflict(DbHelper.Entry.TABLE_MORE_INFO,null,contentValuesMoreInfo,SQLiteDatabase.CONFLICT_REPLACE);
                }
                return true;
            }
            return false;
       }catch (Exception e){
           e.printStackTrace();
           return false;
       }
    }
}
