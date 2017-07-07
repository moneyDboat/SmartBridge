package com.captain.smartbridge.UI.Activity.Monitor;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.other.SensorAdapter;
import com.captain.smartbridge.model.other.Sensor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        List<Sensor> sensors = new ArrayList<>();
        sensors.add(new Sensor("应力传感器1号", "cgqyl1", "应力", "电磁式", "桥面1", "1100"));
        sensors.add(new Sensor("应力传感器2号", "cgqyl2", "应力", "电磁式", "桥面2", "1900"));
        sensors.add(new Sensor("应变传感器1号", "cgqyb1", "应变", "光纤", "桥墩2", "1100"));
        sensors.add(new Sensor("应变传感器2号", "cgqyb2", "应变", "电阻式", "主梁3", "1100"));
        sensors.add(new Sensor("加速度传感器1号", "cgqjsd1", "加速度", "电阻式", "桥墩3", "1100"));
        sensors.add(new Sensor("加速度传感器2号", "cgqjsd2", "加速度", "电阻式", "桥墩5", "1100"));

        SensorAdapter adapter = new SensorAdapter(SensorAcitivty.this, sensors);
        sensorList.setAdapter(adapter);

        sensorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
