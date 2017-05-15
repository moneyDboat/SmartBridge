package com.captain.smartbridge.model;

/**
 * Created by fish on 17-5-15.
 */

public class SimpleText {
    private String title;
    private String context;

    public SimpleText(String title, String context){
        this.title = title;
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
