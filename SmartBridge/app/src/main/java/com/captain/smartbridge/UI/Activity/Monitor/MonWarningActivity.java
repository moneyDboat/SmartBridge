package com.captain.smartbridge.UI.Activity.Monitor;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.SensorDataAdapter;
import com.captain.smartbridge.model.other.MonData;
import com.captain.smartbridge.model.other.MonDataReq;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Captain on 17/8/18.
 */

public class MonWarningActivity extends AbsActivity {
    @BindView(R.id.warning_toolbar)
    Toolbar toolbar;
    @BindView(R.id.warning_list)
    ListView warningList;
    @BindView(R.id.warning_swipe)
    SwipeRefreshLayout warningSwipe;

    List<MonData> warnData = new ArrayList<>();
    SensorDataAdapter adapter = null;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_monitor_warning);
    }

    @Override
    protected void prepareDatas() {
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initList();
    }

    private void initList() {
        String reqjson = getIntent().getStringExtra("req");
        MonDataReq req = new Gson().fromJson(reqjson, MonDataReq.class);

        //获取预警数据
        if (NetUtils.isNetworkAvailable(this)) {
            ApiManager.getmService().monWarnData(req).enqueue(new Callback<List<MonData>>() {
                @Override
                public void onResponse(Call<List<MonData>> call, Response<List<MonData>> response) {
                    if (response.body() == null) {
                        showToast("账户登录过期，请退出账户后重新登录");
                        return;
                    }
                    warnData = response.body();

                    adapter = new SensorDataAdapter(MonWarningActivity.this, warnData);
                    warningList.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<List<MonData>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
