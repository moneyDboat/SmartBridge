package com.captain.smartbridge.UI.Activity;

import android.app.Application;

/**
 * Created by fish on 17-4-26.
 */

public class BaseApplication extends Application {
    private static BaseApplication mApplication;
    private static String ID;

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
}
