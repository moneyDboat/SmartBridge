package com.captain.smartbridge.model;

/**
 * Created by fish on 17-5-16.
 */

public class BridgeList  {
    private String name;
    private String code;
    private String location;

    public BridgeList(String name,String code, String location){
        this.name = name;
        this.code = code;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
