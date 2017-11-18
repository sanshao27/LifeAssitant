package com.ydscience.lifeassistant.bean.news;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ydscience on 2017/6/28.
 */

public class NewState {
    @SerializedName("stat")
    private String stat;
    @SerializedName("data")
    private ArrayList<NewDetailes> data;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public ArrayList<NewDetailes> getData() {
        return data;
    }

    public void setData(ArrayList<NewDetailes> data) {
        this.data = data;
    }
}
