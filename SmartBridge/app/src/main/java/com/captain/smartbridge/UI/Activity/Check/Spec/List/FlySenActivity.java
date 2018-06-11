package com.captain.smartbridge.UI.Activity.Check.Spec.List;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Activity.Check.Spec.PlaneActivity;
import com.captain.smartbridge.UI.Activity.Check.Spec.WaterActivity;
import com.captain.smartbridge.UI.Adapters.tian.MonitorAdapter;
import com.captain.smartbridge.UI.Adapters.tian.SensorAdapter;
import com.captain.smartbridge.model.MonBridge;
import com.captain.smartbridge.model.other.MonSensor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by captain on 18-6-11.
 */

public class FlySenActivity extends AbsActivity{
    @BindView(R.id.flysensor_toolbar)
    Toolbar toolbar;
    @BindView(R.id.flysensor_list)
    ListView sensorList;

    //无人机列表
    List<MonSensor> sensors = new ArrayList<>();
    SensorAdapter adapter = new SensorAdapter(this, sensors);

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_flysenor);
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sensorList.setAdapter(adapter);

        //测试用
        MonSensor sensor = new MonSensor();
        sensor.setCgqmc("无人机1号");
        sensor.setCgqlxmc("图像");
        sensor.setCgqclmc("电磁式");
        sensor.setBswz("");
        sensors.add(sensor);
        adapter.notifyDataSetChanged();
        sensorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                readyGo(PlaneActivity.class);
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
