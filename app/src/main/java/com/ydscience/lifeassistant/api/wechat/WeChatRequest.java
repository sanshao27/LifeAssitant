package com.ydscience.lifeassistant.api.wechat;

import com.ydscience.lifeassistant.api.HttpCacheClient;
import com.ydscience.lifeassistant.bean.wechat.WeChatState;
import com.ydscience.lifeassistant.utils.Commons;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ydscience on 2017/7/6.
 */

public class WeChatRequest {
    public interface WeChatService{
    @GET("wxnew/?key=" + Commons.WECHAT_KEY + "&num=20")
    Observable<WeChatState> getWeChat(@Query("page") int page);
    }

    public static WeChatService getWeChartApi(){
        synchronized (new Object()){
        return new Retrofit.Builder()
                .baseUrl(Commons.weChatUrl)
                .client(new HttpCacheClient().getCacheClient("weChatCache"))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(WeChatService.class);
        }
    }
}
