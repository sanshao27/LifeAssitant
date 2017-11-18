package com.ydscience.lifeassistant.event;

/**
 * Created by ydscience on 2017/9/17.
 */

public class LocationEvent {

    private int result;//0 success 1 failed
    private String city;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
