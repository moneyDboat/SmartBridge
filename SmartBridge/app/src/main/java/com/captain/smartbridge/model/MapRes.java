package com.captain.smartbridge.model;

import java.util.List;

/**
 * Created by Captain on 17/6/5.
 */

public class MapRes {

    /**
     * content : []
     * code : 200
     */

    private int code;
    private List<MapBridge> content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<MapBridge> getContent() {
        return content;
    }

    public void setContent(List<MapBridge> content) {
        this.content = content;
    }
}
