package com.captain.smartbridge.UI.Activity;

import android.app.Application;

import com.captain.smartbridge.model.Account;

/**
 * Created by fish on 17-4-26.
 */

public class BaseApplication extends Application {
    private static BaseApplication mApplication;
    private com.captain.smartbridge.model.Account account;


    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;

    }

    public static BaseApplication getIntance() {
        return mApplication;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
