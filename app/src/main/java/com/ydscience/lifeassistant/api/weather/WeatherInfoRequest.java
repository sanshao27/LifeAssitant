package com.ydscience.lifeassistant.api.weather;

import com.ydscience.lifeassistant.api.HttpCacheClient;
import com.ydscience.lifeassistant.bean.weather.WeatherState;
import com.ydscience.lifeassistant.utils.Commons;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ydscience on 2017/8/22.
 */

public class WeatherInfoRequest {

    public interface WeatherService
    {
        @GET("weather_mini")
        Observable<WeatherState> getWeatherInfo(@Query("city") String city);
    }

    public static WeatherService getWeatherApi()
    {
        synchronized (new Object()){
            return new Retrofit.Builder()
                    .baseUrl(Commons.weatherUrl)
                   // .client(new  HttpCacheClient().getCacheClient("weatherCache"))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(WeatherService.class);
        }
    }
}
