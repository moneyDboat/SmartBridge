package com.captain.smartbridge.UI.Activity.Monitor;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.TextListAdapter;
import com.captain.smartbridge.model.SimpleText;
import com.captain.smartbridge.model.other.MonData;
import com.captain.smartbridge.model.other.MonDataReq;
import com.captain.smartbridge.model.other.MonSensor;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Captain on 17/7/7.
 */

public class SensorCurveActivity extends AbsActivity {
    @BindView(R.id.curve_toolbar)
    Toolbar toolbar;
    @BindView(R.id.curve_chart)
    LineChart chart;
    @BindView(R.id.curve_list)
    ListView curveList;

    List<MonData> data = new ArrayList<>();
    private final Timer timer = new Timer();
    private TimerTask task;

    //for test
//    List<Entry> entries = new ArrayList<>();
//    int i = 0;

    MonSensor sensor = null;
    String bridge = "";

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_curve);
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sensor = new Gson().fromJson(getIntent().getStringExtra("sensor"), MonSensor.class);
        bridge = getIntent().getStringExtra("bridge");

        setTitle(sensor.getCgqmc());
        initChart();
        initList();

        refreshData();
    }

    private void refreshData() {
        //handle句柄
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                getData();
                chart.invalidate();
                super.handleMessage(msg);
            }
        };

        //定时器任务
        task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };

        //启动定时器
        //(*, 延迟执行时间, 循环时间)
        timer.schedule(task, 5000, 1000);
    }

    private void getData() {
        MonDataReq req = new MonDataReq();
        req.setQldm(bridge);
        req.setCgqbh(sensor.getCgqbh());
        if (NetUtils.isNetworkAvailable(this)) {
            ApiManager.getmService().monData(req).enqueue(new Callback<List<MonData>>() {
                @Override
                public void onResponse(Call<List<MonData>> call, Response<List<MonData>> response) {
                    data = response.body();

                    int i = 0;
                    List<Entry> entries = new ArrayList<>();
                    for (MonData da : data) {
                        entries.add(new Entry(i, Float.valueOf(da.getValue())));
                        i++;
                    }
//                    entries.remove(0);
//                    entries.add(new Entry(i, (float) (Math.random() * 10) + 20));
//                    i++;

                    LineDataSet dataSet = new LineDataSet(entries, "传感器数值");
                    //important!!!
                    dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                    dataSet.setCubicIntensity(0.2f);
                    dataSet.setLineWidth(1.8f);
                    dataSet.setColor(Color.WHITE);
                    dataSet.setFillColor(Color.WHITE);
                    dataSet.setFillAlpha(100);

                    LineData lineData = new LineData(dataSet);
                    chart.setData(lineData);
                    chart.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<List<MonData>> call, Throwable t) {
                    t.printStackTrace();
                    showNetWorkError();
                }
            });
        } else {
            showNetWorkError();
        }
    }

    private void initChart() {
//        for (int j = 0; j < 10; j++) {
//            entries.add(new Entry(i, (float) (Math.random() * 10) + 20));
//            i++;
//        }
        getData();

        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(false);
        chart.setViewPortOffsets(0, 0, 0, 0);
        chart.setBackgroundColor(Color.rgb(104, 241, 175));
        chart.animateX(3000);

        XAxis x = chart.getXAxis();
        x.setEnabled(false);

        YAxis y = chart.getAxisLeft();
        y.setTextColor(Color.WHITE);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.WHITE);

        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setEnabled(false);

        chart.invalidate();
    }

    private void initList() {
        List<SimpleText> warns = new ArrayList<>();
        warns.add(new SimpleText("2017.7.4 18:40", "2000"));
        warns.add(new SimpleText("2017.7.4 19:00", "2300"));
        warns.add(new SimpleText("2017.7.5 8:20", "2200"));

        TextListAdapter adapter = new TextListAdapter(SensorCurveActivity.this, warns);
        curveList.setAdapter(adapter);
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
