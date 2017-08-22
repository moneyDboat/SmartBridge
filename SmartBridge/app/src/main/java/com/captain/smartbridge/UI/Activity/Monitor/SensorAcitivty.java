package com.captain.smartbridge.UI.Activity.Monitor;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.other.SensorAdapter;
import com.captain.smartbridge.model.other.MonSensor;
import com.captain.smartbridge.model.other.MonSensorReq;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Captain on 17/7/4.
 */

public class SensorAcitivty extends AbsActivity {
    @BindView(R.id.sensor_toolbar)
    Toolbar toolbar;
    @BindView(R.id.sensor_list)
    ListView sensorList;
    @BindView(R.id.sensor_swipe)
    SwipeRefreshLayout sensorSwipe;

    String bridge = "";
    List<MonSensor> sensors = new ArrayList<>();

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_sensor);
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
        bridge = getIntent().getStringExtra("id");
        MonSensorReq req = new MonSensorReq();
        req.setId(bridge);

        if (NetUtils.isNetworkAvailable(this)) {
            ApiManager.getmService().monSensor(req).enqueue(new Callback<List<MonSensor>>() {
                @Override
                public void onResponse(Call<List<MonSensor>> call, Response<List<MonSensor>> response) {
                    if (response.body() == null) {
                        showToast("账户登录过期，请退出账户后重新登录");
                        return;
                    }

                    sensors = response.body();
                    SensorAdapter adapter = new SensorAdapter(SensorAcitivty.this, sensors);
                    sensorList.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<List<MonSensor>> call, Throwable t) {
                    t.printStackTrace();
                    showNetWorkError();
                }
            });
        } else {
            showNetWorkError();
        }

        sensorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SensorAcitivty.this, SensorCurveActivity.class);
                intent.putExtra("sensor", new Gson().toJson(sensors.get(position)));
                intent.putExtra("bridge", bridge);
                startActivity(intent);
            }
        });
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
}
