package com.captain.smartbridge.UI.Activity;

import android.app.Application;

/**
 * Created by fish on 17-4-26.
 */

public class BaseApplication extends Application {
    private static BaseApplication mApplication;
    private static String ID;

    private static String EVAID;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;

    }

    public static BaseApplication getIntance() {
        return mApplication;
    }

    public static String getID() {
        return ID;
    }

    public static void setID(String ID) {
        BaseApplication.ID = ID;
    }

    public static String getEVAID(){
        return EVAID;
    }

    public static void setEVAID(String ID){
        BaseApplication.EVAID = ID;
    }
}
