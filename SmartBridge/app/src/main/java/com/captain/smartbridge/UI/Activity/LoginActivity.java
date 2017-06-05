package com.captain.smartbridge.UI.Activity;

import android.util.Log;
import android.widget.EditText;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.CommonUtils;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.model.Info;
import com.captain.smartbridge.model.LoginReq;

import butterknife.BindView;
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
    @BindView(R.id.login_username)
    EditText userText;
    @BindView(R.id.login_password)
    EditText passwordText;
    @OnClick(R.id.login_buttom)
    void login() {
//        String username = userText.getText().toString();
//        String pwd = passwordText.getText().toString();
        String username = "system";
        String pwd = "123456";
        if (isValid(username, pwd)){
            LoginReq loginReq = new LoginReq(username, pwd);
            postLogin(loginReq);
        }
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

    private void postLogin(LoginReq loginReq) {
        if (NetUtils.isNetworkConnected(this)){
            ApiManager.getmService().login(loginReq).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.i("Login", response.body().toString());
                    getUserInfo();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    private void getUserInfo() {
        ApiManager.getmService().getInfo().enqueue(new Callback<Info>() {
            @Override
            public void onResponse(Call<Info> call, Response<Info> response) {
                Log.i("Info", response.body().toString());
            }

            @Override
            public void onFailure(Call<Info> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private boolean isValid(String username, String pwd) {
        if (CommonUtils.isEmpty(username)) {
            showToast("请输入您的用户名");
            userText.requestFocus();
            return false;
        }
        if (CommonUtils.isEmpty(pwd)) {
            showToast("请输入您的密码");
            passwordText.requestFocus();
            return false;
        }
        return true;
    }
}
