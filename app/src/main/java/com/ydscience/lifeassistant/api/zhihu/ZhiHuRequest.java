package com.ydscience.lifeassistant.api.zhihu;

import com.ydscience.lifeassistant.api.HttpCacheClient;
import com.ydscience.lifeassistant.bean.zhihu.ZhiHuDaily;
import com.ydscience.lifeassistant.bean.zhihu.ZhiHuStory;
import com.ydscience.lifeassistant.utils.Commons;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ydscience on 2017/7/6.
 */

public class ZhiHuRequest {

    public interface ZhiHuService{
        @GET("api/4/news/latest")
        Observable<ZhiHuDaily> getLastDaily();

        @GET("api/4/news/before/{date}")
        Observable<ZhiHuDaily> getTheDaily(@Path("date") String date);

        @GET("api/4/news/{id}")
        Observable<ZhiHuStory> getZhihuStory(@Path("id") String id);
    }
    public static ZhiHuService getZhiHuInfoApi(){
        synchronized (new Object()){
        return new Retrofit.Builder()
                .baseUrl(Commons.zhiHuUrl)
                .client(new HttpCacheClient().getCacheClient("zhiHuCache"))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ZhiHuService.class);
        }
    }
}
