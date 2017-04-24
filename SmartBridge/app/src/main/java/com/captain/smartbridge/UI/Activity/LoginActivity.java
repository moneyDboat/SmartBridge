package com.captain.smartbridge.UI.Activity;

import android.view.View;

import com.captain.smartbridge.R;

import butterknife.OnClick;

/**
 * Created by fish on 17-4-24.
 */

public class LoginActivity extends AbsActivity {

    @OnClick(R.id.login_buttom) void login(){
        //readygo
    }

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }
}
