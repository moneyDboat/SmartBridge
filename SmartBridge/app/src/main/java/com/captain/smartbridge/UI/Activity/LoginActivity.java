package com.captain.smartbridge.UI.Activity;

import com.captain.smartbridge.R;
import com.captain.smartbridge.model.Account;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fish on 17-4-24.
 */

public class LoginActivity extends AbsActivity {

    private BaseApplication baseApplication;

    @OnClick(R.id.login_buttom) void login(){
        if(postLogin()) {
            readyGo(MainActivity.class);
        }
        else{
            //提示用户名密码不正确
        }

    }

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void prepareDatas() {
        baseApplication = (BaseApplication) getApplication();
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
    }

    private boolean postLogin(){
        Account account = new Account();
        account.setCategory(0);
        baseApplication.setAccount(account);
        return true;
    }
}
