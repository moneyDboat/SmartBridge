package com.captain.smartbridge.model.other;

/**
 * Created by Captain on 17/8/1.
 */

public class MonData{

    /**
     * value : 3
     * time : 2017-08-01 08:27:17
     * voltage : 4.025
     */

    private String time;
    private String value;
    private String voltage;

    public MonData(String time, String value, String voltage){
        this.time = time;
        this.value = value;
        this.voltage = voltage;
    }

    public String getVoltage(){
        return voltage;
    }

    public void setVoltage(String voltage){
        this.voltage = voltage;
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
