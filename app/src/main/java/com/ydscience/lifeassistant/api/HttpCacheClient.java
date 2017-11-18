package com.ydscience.lifeassistant.api;

import android.text.TextUtils;

import com.ydscience.lifeassistant.utils.LifeApplication;
import com.ydscience.lifeassistant.utils.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ydscience on 2017/6/29.
 */

public class HttpCacheClient {
    public  HttpCacheClient(){

    }
    public OkHttpClient getCacheClient(String cachePathName){
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
         File httpCacheDirectory = new File(LifeApplication.getContext().getCacheDir(),cachePathName);
       return  new OkHttpClient.Builder()
               .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
//               .addNetworkInterceptor(interceptor)
               .connectTimeout(6000,TimeUnit.MILLISECONDS)
               .cache(new Cache(httpCacheDirectory,cacheSize)).build();
    }
    Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR =  new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtils.isNetWorkAvailable(LifeApplication.getContext())){
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetWorkUtils.isNetWorkAvailable(LifeApplication.getContext())){
                int maxAge = 0;
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            }else {
                int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };
    Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);

            String cacheControl = request.cacheControl().toString();
            if (TextUtils.isEmpty(cacheControl)) {
                cacheControl = "public, max-age=60";
            }
            return response.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        }
    };

}
