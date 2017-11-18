package com.ydscience.lifeassistant.bean.zhihu;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ydscience on 2017/7/6.
 */

public class ZhiHuDaily {
    @SerializedName("date")
    private String date;
    @SerializedName("stories")
    private ArrayList<ZhiHuDailyItem> stories;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<ZhiHuDailyItem> getStories() {
        return stories;
    }

    public void setStories(ArrayList<ZhiHuDailyItem> stories) {
        this.stories = stories;
    }
}
