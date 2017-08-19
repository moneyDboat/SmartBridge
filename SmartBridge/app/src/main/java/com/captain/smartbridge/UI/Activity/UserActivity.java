package com.captain.smartbridge.UI.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.Common.PreferenceUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.model.InfoRes;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by fish on 17-5-14.
 */

public class UserActivity extends AbsActivity {
    @BindView(R.id.user_toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_nick)
    TextView userNick;
    @BindView(R.id.user_type_text)
    TextView userType;
    @BindView(R.id.user_sf)
    TextView userSf;
    @BindView(R.id.user_depart)
    TextView userDepart;
    @BindView(R.id.user_email)
    TextView userEmail;
    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.user_logout_button)
    Button userLogout;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_user);
    }

    @Override
    protected void prepareDatas() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //init list
        setUserInfo();

        userLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

    }

    private void setUserInfo() {
        if (NetUtils.isNetworkAvailable(this)) {
            ApiManager.getmService().getInfo().enqueue(new Callback<InfoRes>() {
                @Override
                public void onResponse(Call<InfoRes> call, Response<InfoRes> response) {
                    if (response.body() == null) {
                        showToast("账户登录过期，请退出账户后重新登录");
                        return;
                    }

                    InfoRes info = response.body();


                    userDepart.setText(info.getInspectionDepartmentDM());
                    userEmail.setText(info.getEmail());
                    userName.setText(info.getUsername());
                    userNick.setText(info.getNickname());
                    userPhone.setText(info.getPhoneNumber());
                    userSf.setText((info.getSf() == null ? "" : info.getSf())
                            .concat(info.getCs() == null ? "" : info.getCs()));

                }

                @Override
                public void onFailure(Call<InfoRes> call, Throwable t) {
                    showToast("网路出错");
                }
            });
        } else {
            showNetWorkError();
        }
    }


    //退出登录
    //广播通知所有Activity结束
    private void logout() {
        //清楚密码记录
        PreferenceUtils.putString(UserActivity.this, PreferenceUtils.Key.PASS, "");

        Intent intent = new Intent();
        intent.setAction("exit_app");
        sendBroadcast(intent);
        readyGo(LoginActivity.class);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //显示对话框
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否确定退出当前账户？");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
