package com.captain.smartbridge.model;

import java.util.List;

/**
 * Created by captain on 18-6-20.
 */

public class TopDataReq {

    /**
     * id : 48
     * cgqbh : ["cgqnbiotwy1","cgqnbiotwy2","cgqnbiotwy3"]
     */

    private String id;
    private List<String> cgqbh;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getCgqbh() {
        return cgqbh;
    }

    public void setCgqbh(List<String> cgqbh) {
        this.cgqbh = cgqbh;
    }
}
