package com.captain.smartbridge.model.other;

/**
 * Created by Captain on 17/8/1.
 */

public class MonData{

    /**
     * value : 3
     * time : 2017-08-01 08:27:17
     */

    private String time;
    private String value;

    public MonData(String time, String value){
        this.time = time;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
