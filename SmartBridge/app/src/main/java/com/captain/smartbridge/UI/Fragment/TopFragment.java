package com.captain.smartbridge.UI.Fragment;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Adapters.SensorDataAdapter;
import com.captain.smartbridge.UI.Adapters.tian.WarnListAdapter;
import com.captain.smartbridge.UI.View.BatteryView;
import com.captain.smartbridge.model.other.MonData;
import com.captain.smartbridge.model.other.MonDataReq;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by captain on 18-5-10.
 */

public class TopFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ID = "";
    private int mPage;
    private View view;

    List<MonData> data = new ArrayList<>();
    List<MonData> warnData = new ArrayList<>();

    //避免重复调用，造成内存泄漏
    IAxisValueFormatter formatter = null;
    List<Entry> entries = null;
    List<String> dates = null;
    LineDataSet dataSet = null;
    MonDataReq req = null;
    MonDataReq warnReq = null;

    static String id;
    static String sensor;
    Boolean first = false;

    SensorDataAdapter adapter = null;

    final Timer timer = new Timer();
    TimerTask task;
    LineChart chart;
    ListView listView;

    public static TopFragment newInstance(int page, String tId, String tSensor) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        TopFragment fragment = new TopFragment();
        fragment.setArguments(args);

        id = tId;
        sensor = tSensor;

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        switch (mPage) {
            case 0:
                return firstView(inflater, container);
            case 1:
                return secView(inflater, container);
            case 2:
                return thirdView(inflater, container);
            default:
                return null;
        }
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    private View firstView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_four1, container, false);
        BatteryView battery = (BatteryView) view.findViewById(R.id.four1_battery);
        Spinner spinner = (Spinner) view.findViewById(R.id.four1_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //切换曲线（当天数据，本周数据，本月数据）
                String[] times = getResources().getStringArray(R.array.time);
                //Toast.makeText(getActivity(), "你点击的是：" + times[position], 2000).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        chart = (LineChart) view.findViewById(R.id.four1_chart);
        listView = (ListView) view.findViewById(R.id.four1_list);

        initChart(chart);
        refreshData();

        return view;
    }

    private View secView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_four2, container, false);
        final ListView warnList = (ListView) view.findViewById(R.id.four2_list);
        final TextView warnCount = (TextView) view.findViewById(R.id.four2_warnCount);

        warnReq = new MonDataReq();
        warnReq.setId(id);
        warnReq.setCgqbh(sensor);
        warnReq.setNumber("-1000");

        if (NetUtils.isNetworkAvailable(getActivity())) {
            ApiManager.getmService().monWarnData(warnReq).enqueue(new Callback<List<MonData>>() {
                @Override
                public void onResponse(Call<List<MonData>> call, Response<List<MonData>> response) {
                    if (response.body() == null) {
                        //调试用
                        //showDialog();
                        timer.cancel();
                        return;
                    }
                    warnData = response.body();
                    Collections.reverse(warnData);
                    warnCount.setText(String.valueOf(warnData.size()));
                    WarnListAdapter warnAdapter = new WarnListAdapter(getActivity(), warnData);
                    warnList.setAdapter(warnAdapter);
                    warnAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<MonData>> call, Throwable t) {

                }
            });
        }
        return view;
    }

    private View thirdView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_top, container, false);
        final RelativeLayout linear1 = (RelativeLayout) view.findViewById(R.id.fra_top_lay1);
        final ImageView img1 = (ImageView) view.findViewById(R.id.fra_top_img1);
        final LinearLayout out1 = (LinearLayout) view.findViewById(R.id.fra_top_out1);
        final RelativeLayout linear2 = (RelativeLayout) view.findViewById(R.id.fra_top_lay2);
        final ImageView img2 = (ImageView) view.findViewById(R.id.fra_top_img2);
        final LinearLayout out2 = (LinearLayout) view.findViewById(R.id.fra_top_out2);
        final RelativeLayout linear3 = (RelativeLayout) view.findViewById(R.id.fra_top_lay3);
        final ImageView img3 = (ImageView) view.findViewById(R.id.fra_top_img3);
        final LinearLayout out3 = (LinearLayout) view.findViewById(R.id.fra_top_out3);

        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (out1.getVisibility() == View.VISIBLE){
                    out1.setVisibility(View.GONE);
                    img1.setImageResource(R.drawable.down_white);
                }else {
                    out1.setVisibility(View.VISIBLE);
                    img1.setImageResource(R.drawable.right_white);
                }
                out2.setVisibility(View.GONE);
                img2.setImageResource(R.drawable.down_white);
                out3.setVisibility(View.GONE);
                img3.setImageResource(R.drawable.down_white);
            }
        });
        linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (out2.getVisibility() == View.VISIBLE){
                    out2.setVisibility(View.GONE);
                    img2.setImageResource(R.drawable.down_white);
                }else {
                    out2.setVisibility(View.VISIBLE);
                    img2.setImageResource(R.drawable.right_white);
                }
                out1.setVisibility(View.GONE);
                img1.setImageResource(R.drawable.down_white);
                out3.setVisibility(View.GONE);
                img3.setImageResource(R.drawable.down_white);
            }
        });
        linear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (out3.getVisibility() == View.VISIBLE){
                    out3.setVisibility(View.GONE);
                    img3.setImageResource(R.drawable.down_white);
                }else {
                    out3.setVisibility(View.VISIBLE);
                    img3.setImageResource(R.drawable.right_white);
                }
                out1.setVisibility(View.GONE);
                img1.setImageResource(R.drawable.down_white);
                out2.setVisibility(View.GONE);
                img2.setImageResource(R.drawable.down_white);
            }
        });


        return view;

    }

    //显示对话框
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("当前传感器没有数据！");
        //builder.setNegativeButton("取消", null);
        //builder.setCancelable(true);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getActivity().finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void refreshData() {
        //handle句柄
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                getData();
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
        timer.schedule(task, 0, 1000);
    }

    private void getData() {

        req = new MonDataReq();
        req.setId(id);
        req.setCgqbh(sensor);
        req.setNumber("-10");

        if (NetUtils.isNetworkAvailable(getActivity())) {
            ApiManager.getmService().monData(req).enqueue(new Callback<List<MonData>>() {
                @Override
                public void onResponse(Call<List<MonData>> call, Response<List<MonData>> response) {
                    if (response.body() == null) {
                        return;
                    }

                    data = response.body();
                    if (data.size() == 0) {
                        //对话框显示当前传感器没有数据并退出
                        showDialog();
                        return;
                    }

                    List<MonData> listData = new ArrayList<>();
                    listData = data;
                    WarnListAdapter adapter = new WarnListAdapter(getActivity(), listData);
                    listView.setAdapter(adapter);

                    //清空传感器数据
                    entries = new ArrayList<>();
                    dates = new ArrayList<>();
                    int i = 0;
                    for (MonData da : data) {
                        entries.add(new Entry(i, Float.valueOf(da.getValue())));
                        String time = da.getTime().split(" ")[1];
                        dates.add(time);
                        i++;
                    }
                    dataSet = new LineDataSet(entries, "传感器数值");
                    //important!!!
                    dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
                    //dataSet.setCubicIntensity(0.05f);
                    dataSet.setLineWidth(1.8f);
                    dataSet.setColor(Color.WHITE);
                    dataSet.setFillColor(Color.WHITE);
                    dataSet.setFillAlpha(100);
                    dataSet.setDrawCircles(false);
                    dataSet.setDrawValues(false);

                    LineData lineData = new LineData(dataSet);
                    chart.setData(lineData);

                    //设置折线图横坐标
                    formatter = new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            try {
                                return dates.get((int) value);
                            } catch (IndexOutOfBoundsException e) {
                                return "error";
                            }
                        }

                        @Override
                        public int getDecimalDigits() {
                            return 0;
                        }
                    };
                    XAxis xAxis = chart.getXAxis();
                    chart.invalidate();
                }

                @Override
                public void onFailure(Call<List<MonData>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    private void initChart(LineChart chart) {
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(false);
        chart.setViewPortOffsets(0, 0, 0, 0);
        //chart.setBackgroundColor(Color.rgb(104, 241, 175));
        chart.setBackgroundColor(Color.LTGRAY);


        //之后需要去除动画效果
        //chart.animateX(3000);


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
//        LimitLine ll = new LimitLine(sensor.getYz(), "阈值");
//        ll.setLineWidth(4f);
//        ll.enableDashedLine(10f, 10f, 0f);
//        ll.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//        ll.setTextSize(10f);
//        y.addLimitLine(ll);
        y.setDrawLimitLinesBehindData(true);

        chart.invalidate();
    }
}
