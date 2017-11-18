package com.ydscience.lifeassistant.api.news;

import com.ydscience.lifeassistant.api.HttpCacheClient;
import com.ydscience.lifeassistant.bean.news.NewResult;
import com.ydscience.lifeassistant.utils.Commons;
import com.ydscience.lifeassistant.utils.LifeApplication;
import com.ydscience.lifeassistant.utils.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ydscience on 2017/6/28.
 */

public class CommonNewRequest {

    public interface NewService{
        @GET("index")
        Observable<NewResult> getNewInfo(@Query("type") String type,@Query("key") String key);
    }
    public static NewService getNewApi(){
        synchronized (new Object()){
            return new Retrofit.Builder()
                    .baseUrl(Commons.newUrl)
                    .client(new HttpCacheClient().getCacheClient("newCache"))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(NewService.class);
        }
    }



}
