package com.ydscience.lifeassistant.bean.news;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ydscience on 2017/6/28.
 */

public class NewResult {
    @SerializedName("reason")
    private String reason;
    @SerializedName("result")
    private NewState result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public NewState getResult() {
        return result;
    }

    public void setResult(NewState result) {
        this.result = result;
    }
}
