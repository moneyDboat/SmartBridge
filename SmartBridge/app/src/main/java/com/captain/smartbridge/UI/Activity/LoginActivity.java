package com.captain.smartbridge.UI.Activity;

import android.util.Log;
import android.widget.EditText;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.CommonUtils;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.Common.PreferenceUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.model.Info;
import com.captain.smartbridge.model.InfoRes;
import com.captain.smartbridge.model.LoginReq;
import com.dd.processbutton.iml.ActionProcessButton;

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

public class LoginActivity extends AbsActivity {
    @BindView(R.id.login_username)
    EditText userText;
    @BindView(R.id.login_password)
    EditText passwordText;
    @BindView(R.id.login_buttom)
    ActionProcessButton loginButtom;

    @OnClick(R.id.login_buttom)
    void login() {
        //        String username = userText.getText().toString();
        //        String pwd = passwordText.getText().toString();
        loginButtom.setMode(ActionProcessButton.Mode.PROGRESS);
        loginButtom.setProgress(0);
        loginButtom.setEnabled(false);
        String username = "fansen";
        String pwd = "123456";
        if (isValid(username, pwd)) {
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
        if (NetUtils.isNetworkConnected(this)) {
            ApiManager.getmService().login(loginReq).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.i("Login", response.body().toString());
                    loginButtom.setProgress(50);
                    getUserInfo();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    loginButtom.setProgress(-1);
                    loginButtom.setEnabled(true);
                }
            });
        } else {
            showNetWorkError();
            loginButtom.setProgress(-1);
            loginButtom.setEnabled(true);
        }
    }

    private void getUserInfo() {
        ApiManager.getmService().getInfo().enqueue(new Callback<InfoRes>() {
            @Override
            public void onResponse(Call<InfoRes> call, Response<InfoRes> response) {
                if (response.body().getCode() == 200) {
                    loginButtom.setProgress(100);
                    saveUserInfo(response.body().getContent());
                    readyGoThenKill(MainActivity.class);
                } else {
                    showToast("用户名密码错误");
                    loginButtom.setProgress(-1);
                    loginButtom.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<InfoRes> call, Throwable t) {
                t.printStackTrace();
                loginButtom.setProgress(-1);
                loginButtom.setEnabled(true);
            }
        });

    }

    private void saveUserInfo(Info info) {
        PreferenceUtils.putString(this, PreferenceUtils.Key.USER, info.getUsername());
        PreferenceUtils.putInt(this, PreferenceUtils.Key.ROLE, info.getRoleType());
        //        PreferenceUtils.putString(this, PreferenceUtils.Key.DATE, info.getRegisterDate());
        PreferenceUtils.putString(this, PreferenceUtils.Key.HEADPIC, info.getHeadPortrait());
        PreferenceUtils.putString(this, PreferenceUtils.Key.SF, info.getSf());
        PreferenceUtils.putString(this, PreferenceUtils.Key.CF, info.getCs());
        PreferenceUtils.putInt(this, PreferenceUtils.Key.ID, info.getUserId());
        //        PreferenceUtils.putString(this, PreferenceUtils.Key.DEPART, info.getInspectionDepartmentDM());
        PreferenceUtils.putString(this, PreferenceUtils.Key.PHONE, info.getPhoneNumber());
        PreferenceUtils.putString(this, PreferenceUtils.Key.NICK, info.getNickname());
        PreferenceUtils.putString(this, PreferenceUtils.Key.EMAIL, info.getEmail());
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
