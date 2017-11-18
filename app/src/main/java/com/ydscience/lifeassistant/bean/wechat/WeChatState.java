package com.ydscience.lifeassistant.bean.wechat;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ydscience on 2017/7/6.
 */

public class WeChatState {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("newslist")
    private ArrayList<WeChatNews> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<WeChatNews> getNewslist() {
        return newslist;
    }

    public void setNewslist(ArrayList<WeChatNews> newslist) {
        this.newslist = newslist;
    }
}
