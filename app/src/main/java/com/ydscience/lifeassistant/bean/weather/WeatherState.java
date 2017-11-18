package com.ydscience.lifeassistant.bean.weather;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ydscience on 2017/8/22.
 */

public class WeatherState {

    @SerializedName("data")
    private WeatherCurrentInfo data;

    @SerializedName("status")
    private int status;

    @SerializedName("desc")
    private String desc;

    public WeatherCurrentInfo getData() {
        return data;
    }

    public void setData(WeatherCurrentInfo data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
