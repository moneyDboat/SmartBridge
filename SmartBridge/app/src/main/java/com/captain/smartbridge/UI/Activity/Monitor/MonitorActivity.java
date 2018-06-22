package com.captain.smartbridge.UI.Activity.Monitor;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.captain.smartbridge.UI.Activity.Monitor.Wireless.TopDateActivity;
import com.captain.smartbridge.model.other.MonSensor;
import com.captain.smartbridge.model.other.MonSensorReq;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by captain on 18-3-23.
 */

public class MonitorActivity extends AbsActivity implements View.OnClickListener {
    @BindView(R.id.monitor_toolbar)
    Toolbar toolbar;
    @BindView(R.id.monitor_bridge)
    TextView bridgeText;
    @BindView(R.id.pic_things)
    LinearLayout picThings;
    @BindView(R.id.pic_4g)
    LinearLayout pic4g;
    @BindView(R.id.pic_pic)
    LinearLayout picPic;
    @BindView(R.id.pic_top)
    LinearLayout picTop;
    @BindView(R.id.text_speed)
    TextView textSpeed;
    @BindView(R.id.text_support)
    TextView textSupport;
    @BindView(R.id.text_flex)
    TextView textFlex;

    String bridge = "";
    String id = "";

    HashMap<Integer, String> maps = new HashMap<>();

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_monitor);
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //获取桥梁id
        bridge = getIntent().getStringExtra("bridge");
        id = getIntent().getStringExtra("id");

        maps.put(R.id.pic_things, "nbiot");
        maps.put(R.id.pic_4g, "4G");
        maps.put(R.id.pic_pic, "图像");
        maps.put(R.id.pic_top, "top");
        maps.put(R.id.text_speed, "speed");
        maps.put(R.id.text_support, "support");
        maps.put(R.id.text_flex, "flex");

        bridgeText.setText("当前桥梁：" + bridge);
        picThings.setOnClickListener(this);
        pic4g.setOnClickListener(this);
        picPic.setOnClickListener(this);
        picTop.setOnClickListener(this);
        textSpeed.setOnClickListener(this);
        textSupport.setOnClickListener(this);
        textFlex.setOnClickListener(this);
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
    public void onClick(View v) {
        //顶升检测会跳转到单独的界面，其他都是跳转到对应的传感器列表
        if (v.getId() == R.id.pic_top) {
            getTopSensor();
        } else {
            Intent intent = new Intent(getApplication(), SensorAcitivty.class);
            intent.putExtra("id", id);
            //选择监测种类
            intent.putExtra("type", maps.get(v.getId()));
            startActivity(intent);
        }
    }

    //提前请求所有顶升传感器，传给顶升界面
    private void getTopSensor() {
        MonSensorReq req = new MonSensorReq();
        req.setId(id);

        if (NetUtils.isNetworkAvailable(this)) {
            ApiManager.getmService().monSensor(req).enqueue(new Callback<List<MonSensor>>() {
                @Override
                public void onResponse(Call<List<MonSensor>> call, Response<List<MonSensor>> response) {
                    if (response.body() == null) {
                        return;
                    }

                    //筛选顶升传感器
                    List<MonSensor> sensors = new ArrayList<>();
                    for (MonSensor data : response.body()) {
                        //顶升根据传感器类型名称字段判断
                        if (data.getCgqlxmc().contains("应变") || data.getCgqlxmc().contains("位移"))
                            sensors.add(data);
                    }

                    Intent intent = new Intent(getApplication(), TopActivity.class);
                    Gson gson = new Gson();
                    intent.putExtra("sensors", gson.toJson(sensors));
                    intent.putExtra("id", id);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<List<MonSensor>> call, Throwable t) {

                }
            });
        }
    }
}
