package com.captain.smartbridge.UI.Activity;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.R;
import com.captain.smartbridge.model.LoginReq;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by fish on 17-6-2.
 */

public class LoginActivity extends AbsActivity{
    @OnClick(R.id.login_buttom)
    void login() {
        postLogin();
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
        ButterKnife.bind(this);
    }

    private void postLogin() {
        LoginReq loginReq = new LoginReq("hah", "hah");
        Call<ResponseBody> call = ApiManager.getmService().login(loginReq);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void getUserInfo() {

    }
}
