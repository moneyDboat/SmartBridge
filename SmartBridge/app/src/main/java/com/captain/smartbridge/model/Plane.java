package com.captain.smartbridge.model;

/**
 * Created by captain on 18-5-15.
 */

public class Plane {
    private String name;
    private String date;

    public Plane(String name, String date){
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
