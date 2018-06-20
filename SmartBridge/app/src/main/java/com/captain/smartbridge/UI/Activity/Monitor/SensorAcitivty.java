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
import com.captain.smartbridge.UI.Activity.Monitor.Noise.FlexActivity;
import com.captain.smartbridge.UI.Activity.Monitor.Noise.SpeedActivity;
import com.captain.smartbridge.UI.Activity.Monitor.Noise.SupportActivity;
import com.captain.smartbridge.UI.Activity.Monitor.Wireless.FourGActivity;
import com.captain.smartbridge.UI.Activity.Monitor.Wireless.PicActivity;
import com.captain.smartbridge.UI.Activity.Monitor.Wireless.ThingsActivity;
import com.captain.smartbridge.UI.Activity.Monitor.Wireless.TopActivity;
import com.captain.smartbridge.UI.Adapters.other.SensorAdapter;
import com.captain.smartbridge.model.other.MonSensor;
import com.captain.smartbridge.model.other.MonSensorReq;
import com.captain.smartbridge.model.other.Monitor;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
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

    String bridgeId = "";
    //监测种类
    String type = "";
    List<MonSensor> sensors = new ArrayList<>();

    HashMap<String, Class<?>> maps = new HashMap<>();

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

        maps.put("nbiot", ThingsActivity.class);
        maps.put("4G", FourGActivity.class);
        maps.put("图像", PicActivity.class);
        maps.put("top", TopActivity.class);
        maps.put("speed", SpeedActivity.class);
        maps.put("support", SupportActivity.class);
        maps.put("flex", FlexActivity.class);

        initList();
    }

    private void initList() {
        bridgeId = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");
        MonSensorReq req = new MonSensorReq();
        req.setId(bridgeId);

        if (NetUtils.isNetworkAvailable(this)) {
            ApiManager.getmService().monSensor(req).enqueue(new Callback<List<MonSensor>>() {
                @Override
                public void onResponse(Call<List<MonSensor>> call, Response<List<MonSensor>> response) {
                    if (response.body() == null) {
                        showToast("账户登录过期，请退出账户后重新登录");
                        return;
                    }

                    //根据监测种类筛选传感器列表
                    List<MonSensor> tmpData = response.body();
                    for (MonSensor data : tmpData) {
                        //除了顶升其他根据传感器名称判断
                        if (data.getCgqmc().startsWith(type)) {
                            sensors.add(data);
                        }
                    }

                    SensorAdapter adapter = new SensorAdapter(SensorAcitivty.this, sensors);
                    sensorList.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<List<MonSensor>> call, Throwable t) {
                    showNetWorkError();
                }
            });
        } else {
            showNetWorkError();
        }

        sensorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class<?> cls = maps.get(type);
                Intent intent = new Intent(SensorAcitivty.this, cls);
                intent.putExtra("id", bridgeId);
                intent.putExtra("sensor", sensors.get(position).getCgqbh());
                //new Gson().toJson(sensors.get(position))
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
