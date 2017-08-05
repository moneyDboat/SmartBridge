package com.captain.smartbridge.model.other;

/**
 * Created by Captain on 17/8/1.
 */

public class MonDataReq {

    /**
     * qldm : G00010005
     * sensor : sensor1
     */

    private String qldm;
    private String sensor;

    public String getQldm() {
        return qldm;
    }

    public void setQldm(String qldm) {
        this.qldm = qldm;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }
}
