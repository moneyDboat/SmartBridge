package com.captain.smartbridge.model;

/**
 * Created by fish on 17-5-17.
 */

public class Mission {
    String name;
    String code;
    String asign;
    String detect;
    int status;

    public Mission(String name, String code, String asign, String detect, int status){
        this.name = name;
        this.code = code;
        this.asign = asign;
        this.detect = detect;
        this.status = status;
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

    public String getAsign() {
        return asign;
    }

    public void setAsign(String asign) {
        this.asign = asign;
    }

    public String getDetect() {
        return detect;
    }

    public void setDetect(String detect) {
        this.detect = detect;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
