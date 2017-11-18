package com.ydscience.lifeassistant.bean.news;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ydscience on 2017/6/28.
 */

public class NewDetailes {
    @SerializedName("title")
    private String title;
    @SerializedName("date")
    private String date;
    @SerializedName("author_name")
    private String authour_name;
    @SerializedName("url")
    private String url;
    @SerializedName("thumbnail_pic_s")
    private String thumbnail_pic_s;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthour_name() {
        return authour_name;
    }

    public void setAuthour_name(String authour_name) {
        this.authour_name = authour_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }
}
