package com.captain.smartbridge.UI.Activity;

import android.os.Bundle;
import android.widget.EditText;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.CommonUtils;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.Common.PreferenceUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.model.InfoRes;
import com.captain.smartbridge.model.LoginReq;
import com.dd.processbutton.iml.ActionProcessButton;
import com.github.florent37.materialtextfield.MaterialTextField;

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
    @BindView(R.id.login_userField)
    MaterialTextField loginUserField;
    @BindView(R.id.login_pwdField)
    MaterialTextField loginPwdField;

    String username = "";
    String pwd = "";

    @OnClick(R.id.login_buttom)
    void login() {
        username = userText.getText().toString();
        pwd = passwordText.getText().toString();
        loginButtom.setMode(ActionProcessButton.Mode.PROGRESS);
        loginButtom.setProgress(0);
        if (isValid(username, pwd)) {
            LoginReq loginReq = new LoginReq(username, pwd);
            loginButtom.setEnabled(false);
            postLogin(loginReq, false);
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

        username = PreferenceUtils.getString(this, PreferenceUtils.Key.USER);
        pwd = PreferenceUtils.getString(this, PreferenceUtils.Key.PASS);
        userText.setText(username);

        loginUserField.setHasFocus(true);

        //如果有密码记录，则自动登录
        if (pwd != "") {
            passwordText.setText(pwd);
            loginPwdField.setHasFocus(true);
            loginButtom.setProgress(100);
            loginButtom.setText("自动登录中");
            readyGoThenKill(MainActivity.class);
            LoginReq loginReq = new LoginReq(username, pwd);
            postLogin(loginReq, true);
        }

    }

    private void postLogin(LoginReq loginReq, final Boolean autoLogin) {
        if (NetUtils.isNetworkConnected(this)) {
            ApiManager.getmService().login(loginReq).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.body() == null) {
                        showToast("用户名密码错误！");
                        loginButtom.setEnabled(true);
                        return;
                    }
                    loginButtom.setProgress(50);
                    if (autoLogin){
                        readyGoThenKill(MainActivity.class);
                    }else{
                        getUserInfo();
                    }
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
                loginButtom.setProgress(100);
                saveUserInfo(response.body());
                readyGoThenKill(MainActivity.class);
            }

            @Override
            public void onFailure(Call<InfoRes> call, Throwable t) {
                showToast("用户名密码错误");
                loginButtom.setProgress(-1);
                loginButtom.setEnabled(true);
            }
        });

    }

    private void saveUserInfo(InfoRes info) {
        PreferenceUtils.putString(this, PreferenceUtils.Key.USER, info.getUsername());
        PreferenceUtils.putInt(this, PreferenceUtils.Key.ROLE, info.getRoleType());
        //        PreferenceUtils.putString(this, PreferenceUtils.Key.DATE, info.getRegisterDate());
        PreferenceUtils.putString(this, PreferenceUtils.Key.HEADPIC, info.getHeadPortrait());
        PreferenceUtils.putString(this, PreferenceUtils.Key.SF, info.getSf() == null ? "" : info.getSf());
        PreferenceUtils.putString(this, PreferenceUtils.Key.CF, info.getCs() == null ? "" : info.getCs());
        PreferenceUtils.putInt(this, PreferenceUtils.Key.ID, info.getUserId());
        //        PreferenceUtils.putString(this, PreferenceUtils.Key.DEPART, info.getInspectionDepartmentDM());
        PreferenceUtils.putString(this, PreferenceUtils.Key.PHONE, info.getPhoneNumber());
        PreferenceUtils.putString(this, PreferenceUtils.Key.NICK, info.getNickname());
        PreferenceUtils.putString(this, PreferenceUtils.Key.EMAIL, info.getEmail());
        PreferenceUtils.putString(this, PreferenceUtils.Key.PASS, pwd);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
