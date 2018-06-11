package com.captain.smartbridge.model.other;

/**
 * Created by Captain on 17/8/1.
 */

public class MonDataReq {

    /**
     * id : 35
     * cgqbh : cgqjsd1
     * number : -10
     * StartTime : 2018-06-04 00:00:00
     * StopTime : 2018-06-05 00:00:00
     */

    private String id;
    private String cgqbh;
    private String number;
    private String StartTime;
    private String StopTime;

    public MonDataReq(){
        StartTime = "";
        StopTime = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCgqbh() {
        return cgqbh;
    }

    public void setCgqbh(String cgqbh) {
        this.cgqbh = cgqbh;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getStopTime() {
        return StopTime;
    }

    public void setStopTime(String stopTime) {
        StopTime = stopTime;
    }
}
