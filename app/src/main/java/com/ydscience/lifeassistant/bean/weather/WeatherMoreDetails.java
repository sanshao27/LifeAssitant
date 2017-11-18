package com.ydscience.lifeassistant.bean.weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ydscience on 2017/8/22.
 */

public class WeatherMoreDetails {

    @SerializedName("date")
    private String date;

    @SerializedName("high")
    private String high;

    @SerializedName("fengli")
    private String fengli;

    @SerializedName("low")
    private String low;

    @SerializedName("fengxiang")
    private String fengxiang;

    @SerializedName("type")
    private String type;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getFengli() {
        return fengli;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getFengxiang() {
        return fengxiang;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
