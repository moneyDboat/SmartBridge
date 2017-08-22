package com.captain.smartbridge.model.other;

/**
 * Created by Captain on 17/8/1.
 */

public class MonSensor {

    /**
     * cgqlxmc : 应力
     * jtwz : 1
     * bswz : 桥面
     * cgqclmc : 电磁式
     * cgqmc : 应力传感器1号
     * yz : 100.0
     * cgqbh : cgqyl1
     * id : 35
     */

    private String cgqlxmc;
    private String jtwz;
    private String bswz;
    private String cgqclmc;
    private String cgqmc;
    private Float yz;
    private String cgqbh;
    private int id;

    public String getCgqlxmc() {
        return cgqlxmc;
    }

    public void setCgqlxmc(String cgqlxmc) {
        this.cgqlxmc = cgqlxmc;
    }

    public String getJtwz() {
        return jtwz;
    }

    public void setJtwz(String jtwz) {
        this.jtwz = jtwz;
    }

    public String getBswz() {
        return bswz;
    }

    public void setBswz(String bswz) {
        this.bswz = bswz;
    }

    public String getCgqclmc() {
        return cgqclmc;
    }

    public void setCgqclmc(String cgqclmc) {
        this.cgqclmc = cgqclmc;
    }

    public String getCgqmc() {
        return cgqmc;
    }

    public void setCgqmc(String cgqmc) {
        this.cgqmc = cgqmc;
    }

    public float getYz() {
        return yz;
    }

    public void setYz(float yz) {
        this.yz = yz;
    }

    public String getCgqbh() {
        return cgqbh;
    }

    public void setCgqbh(String cgqbh) {
        this.cgqbh = cgqbh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
