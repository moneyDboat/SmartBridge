package com.captain.smartbridge.model;

/**
 * Created by fish on 17-5-15.
 */

public class SimpleTexts {
    private String name;
    private String kind;
    private String num;
    private String category;

    public SimpleTexts(String name,String kind,String num,String category){
        this.name = name;
        this.kind = kind;
        this.num = num;
        this.category = category;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
