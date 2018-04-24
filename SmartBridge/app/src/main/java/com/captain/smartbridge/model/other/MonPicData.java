package com.captain.smartbridge.model.other;

/**
 * Created by Captain on 18/4/24.
 */

public class MonPicData {

    private String qiniuurl;
    private String filename;
    private String time;
    private String size;

    public MonPicData(String qiniuurl, String filename, String time, String size){
        this.qiniuurl = qiniuurl;
        this.filename = filename;
        this.time = time;
        this.size = size;
    }

    public String getQiniuurl() {
        return qiniuurl;
    }

    public void setQiniuurl(String qiniuurl) {
        this.qiniuurl = qiniuurl;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }



}
