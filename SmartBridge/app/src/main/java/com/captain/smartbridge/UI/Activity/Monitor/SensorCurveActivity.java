package com.captain.smartbridge.UI.Activity.Monitor;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.SensorDataAdapter;
import com.captain.smartbridge.model.other.MonData;
import com.captain.smartbridge.model.other.MonDataReq;
import com.captain.smartbridge.model.other.MonSensor;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
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

public class SensorCurveActivity extends AbsActivity{
    @BindView(R.id.curve_toolbar)
    Toolbar toolbar;
    @BindView(R.id.curve_chart)
    LineChart chart;
    @BindView(R.id.curve_list)
    ListView curveList;
    @BindView(R.id.curve_more)
    TextView curveMore;
    @BindView(R.id.curve_num)
    TextView curveNum;
    @BindView(R.id.curve_seekbar)
    SeekBar curveSeekbar;

    List<MonData> data = new ArrayList<>();
    List<MonData> warnData = new ArrayList<>();
    private final Timer timer = new Timer();
    private TimerTask task;
    //传感器电量&数据条数
    private int ele = 100;
    private int num = 10;
    private int max = 100;

    //for test
    List<MonData> testData = new ArrayList<>();
    List<String> dates;
    int year = 2000;

    //避免重复调用，造成内存泄漏
    IAxisValueFormatter formatter = null;
    List<Entry> entries = null;
    LineDataSet dataSet = null;
    MonDataReq req = null;
    MonDataReq warnReq = null;

    MonSensor sensor = null;
    String bridge = "";
    Boolean first = false;

    SensorDataAdapter adapter = null;

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

        initList();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sensor = new Gson().fromJson(getIntent().getStringExtra("sensor"), MonSensor.class);
        bridge = getIntent().getStringExtra("bridge");

        setTitle(sensor.getCgqmc());
        initChart();

        //init seekbar
        curveSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //重新获取数据
                num = progress+10;
                curveNum.setText(""+num);

                dataSet.setValues(entries.subList(0,num));
                dataSet.setDrawValues(num<11);
                LineData lineData = new LineData(dataSet);
                chart.setData(lineData);
                chart.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


                //getData();
            }
        });
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
        timer.schedule(task, 2000, 1000);
    }

    private void getData() {
        req = new MonDataReq();
        req.setId(bridge);
        req.setCgqbh(sensor.getCgqbh());
        //req.setNumber("-"+num);//默认值为－10，代表最近的十条数据
        req.setNumber("-"+max);

        warnReq = new MonDataReq();
        warnReq.setId(bridge);
        warnReq.setCgqbh(sensor.getCgqbh());
        warnReq.setNumber("-4");

        if (NetUtils.isNetworkAvailable(this)) {
            ApiManager.getmService().monData(req).enqueue(new Callback<List<MonData>>() {
                @Override
                public void onResponse(Call<List<MonData>> call, Response<List<MonData>> response) {
                    if (response.body() == null) {
                        showToast("账户登录过期，请退出账户后重新登录");
                        return;
                    }

                    data = response.body();

                    //使用测试数据数据
                    //                    testData.remove(0);
                    //                    testData.add(new MonData(String.valueOf(year++),
                    //                            String.valueOf(sensor.getYz() + (Math.random() - 0.7) * 100)));
                    //                    data = testData;

                    if (data.size() == 0) {
                        showDialog();
                        return;
                    }

                    dates = new ArrayList<>();

                    int i = 0;
                    entries = new ArrayList<>();
                    for (MonData da : data) {
                        entries.add(new Entry(i, Float.valueOf(da.getValue())));

                        //这部分之后需要添加时间值的处理，切割字段
                        String time = da.getTime().split(" ")[1];
                        dates.add(time);

                        i++;
                    }


                    //获取预警数据
                    getWarnData();

                    dataSet = new LineDataSet(entries.subList(0,num), "传感器数值");
                    //important!!!
                    dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
                    //dataSet.setCubicIntensity(0.05f);
                    dataSet.setLineWidth(1.8f);
                    dataSet.setColor(Color.WHITE);
                    dataSet.setFillColor(Color.WHITE);
                    dataSet.setFillAlpha(100);

                    //如果数据数量超过10就不显示具体数值
                    dataSet.setDrawValues(num<11);

                    LineData lineData = new LineData(dataSet);
                    chart.setData(lineData);

                    //设置折线图横坐标
                    formatter = new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            try {
                                return dates.get((int) value);
                            }catch (IndexOutOfBoundsException e){
                                return "error";
                            }
                        }

                        @Override
                        public int getDecimalDigits() {
                            return 0;
                        }
                    };
                    XAxis xAxis = chart.getXAxis();
                    xAxis.setValueFormatter(formatter);


                    //实时获取数据
                    if (first) {
                        refreshData();
                        first = false;
                    }
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

    private void getWarnData() {

        ApiManager.getmService().monWarnData(warnReq).enqueue(new Callback<List<MonData>>() {
            @Override
            public void onResponse(Call<List<MonData>> call, Response<List<MonData>> response) {
                if (response.body() == null) {
                    showToast("账户登录过期，请退出账户后重新登录");
                    return;
                }
                warnData = response.body();
                //最多只显示四行
                if (warnData.size() > 4) {
                    warnData = warnData.subList(0, 3);
                }
                Collections.reverse(warnData);
                adapter.notifyDataSetChanged();
                adapter = new SensorDataAdapter(SensorCurveActivity.this, warnData);
                curveList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<MonData>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void initChart() {
        //生成模拟数据
        //        for (int j = 0; j < 10; j++) {
        //            testData.add(new MonData(String.valueOf(year++),
        //                    String.valueOf(sensor.getYz() + (Math.random() - 0.7) * 100)));
        //        }
        getData();

        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(false);
        chart.setViewPortOffsets(0, 0, 0, 0);
        //chart.setBackgroundColor(Color.rgb(104, 241, 175));
        chart.setBackgroundColor(Color.LTGRAY);


        //之后需要去除动画效果
        chart.animateX(3000);


        XAxis x = chart.getXAxis();
        x.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        //x.setDrawGridLines(false);
        x.setGranularity(1f);
        x.setAxisMinimum(1f);
        x.setLabelRotationAngle(-30);
        //x.setLabelRotationAngle(-30);


        YAxis y = chart.getAxisLeft();
        y.setTextColor(Color.WHITE);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.WHITE);

        chart.getAxisRight().setEnabled(false);
        //chart.getLegend().setEnabled(false);

        //图例
        Legend l = chart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);

        //添加阈值线
        //Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        LimitLine ll = new LimitLine(sensor.getYz(), "阈值");
        ll.setLineWidth(4f);
        ll.enableDashedLine(10f, 10f, 0f);
        ll.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll.setTextSize(10f);
        y.addLimitLine(ll);
        y.setDrawLimitLinesBehindData(true);

        chart.invalidate();
    }

    private void initList() {
        adapter = new SensorDataAdapter(SensorCurveActivity.this, warnData);
        curveList.setAdapter(adapter);

        curveMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SensorCurveActivity.this, MonWarningActivity.class);
                intent.putExtra("req", new Gson().toJson(req));
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            case R.id.ele:
                Intent intent = new Intent(SensorCurveActivity.this, eleActivity.class);

                //根据电压数据计算传感器的电量信息
                float v = Float.valueOf(data.get(99).getVoltage());
                ele = (int)(v*100/(4.2));

                //代表传感器电量
                intent.putExtra("ele", ele);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //显示对话框
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("当前传感器没有数据！");
        //builder.setNegativeButton("取消", null);
        //builder.setCancelable(true);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SensorCurveActivity.this.finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //查看电量的按钮
        getMenuInflater().inflate(R.menu.menu_ele, menu);
        return true;
    }
}
