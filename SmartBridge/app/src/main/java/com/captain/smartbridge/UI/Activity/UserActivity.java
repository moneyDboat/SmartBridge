package com.captain.smartbridge.UI.Activity;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.model.Info;
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
    @BindView(R.id.user_type)
    TextView userType;
    @BindView(R.id.user_sf)
    TextView userSf;
    @BindView(R.id.user_depart)
    TextView userDepart;
    @BindView(R.id.user_email)
    TextView userEmail;
    @BindView(R.id.user_phone)
    TextView userPhone;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user);
//        ButterKnife.bind(this);
//
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//    }

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

    }

    private void setUserInfo(){
        if(NetUtils.isNetworkAvailable(this)){
            ApiManager.getmService().getInfo().enqueue(new Callback<InfoRes>() {
                @Override
                public void onResponse(Call<InfoRes> call, Response<InfoRes> response) {
                    if(response.body().getCode()==200){
                        Info info = response.body().getContent();
                        userDepart.setText(info.getInspectionDepartmentDM().toString());
                        userEmail.setText(info.getEmail());
                        userName.setText(info.getUsername());
                        userNick.setText(info.getNickname());
                        userPhone.setText(info.getPhoneNumber());
                        userSf.setText(info.getSf()+info.getCs());
                        userType.setText(info.getRoleType());
                    }else{
                        showToast("出错");
                    }
                }

                @Override
                public void onFailure(Call<InfoRes> call, Throwable t) {

                }
            });
        }else {
            showNetWorkError();
        }
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
}
