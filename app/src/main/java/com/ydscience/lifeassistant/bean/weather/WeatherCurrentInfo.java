package com.ydscience.lifeassistant.bean.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ydscience on 2017/8/22.
 */

public class WeatherCurrentInfo {
    @SerializedName("city")
    private String city;

    @SerializedName("aqi")
    private String aqi;

    @SerializedName("forecast")
    private List<WeatherMoreDetails> forecast;

    @SerializedName("wendu")
    private String wendu;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public List<WeatherMoreDetails> getForecast() {
        return forecast;
    }

    public void setForecast(List<WeatherMoreDetails> forecast) {
        this.forecast = forecast;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }
}
