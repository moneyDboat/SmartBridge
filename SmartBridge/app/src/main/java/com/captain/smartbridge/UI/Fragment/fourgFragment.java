package com.captain.smartbridge.UI.Fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.Monitor.SensorCurveActivity;
import com.captain.smartbridge.UI.Adapters.SensorDataAdapter;
import com.captain.smartbridge.UI.Adapters.tian.WarnListAdapter;
import com.captain.smartbridge.UI.View.BatteryView;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by captain on 18-3-23.
 */

public class fourgFragment extends Fragment {
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


    //是否是4G页面
    private Boolean if4g = false;

    public static fourgFragment newInstance(int page, String tId, String tSensor) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fourgFragment fragment = new fourgFragment();
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

    private View firstView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_four1, container, false);
        BatteryView battery = (BatteryView) view.findViewById(R.id.four1_battery);
        final LineChart chart = (LineChart) view.findViewById(R.id.four1_chart);
        final ListView listView = (ListView) view.findViewById(R.id.four1_list);


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

                    WarnListAdapter adapter = new WarnListAdapter(getActivity(), data);
                    listView.setAdapter(adapter);

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
                    xAxis.setValueFormatter(formatter);

                    initChart(chart);
                }

                @Override
                public void onFailure(Call<List<MonData>> call, Throwable t) {

                }
            });
        }

        return view;
    }

    private View secView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_four2, container, false);
        ListView listView = (ListView) view.findViewById(R.id.four2_list);

        //创建虚拟数据
        List<MonData> data = new ArrayList<>();
        data.add(new MonData("2018-03-14 11:58:31", "-19.187", "0"));
        data.add(new MonData("2018-03-14 11:58:01", "-19.187", "0"));
        data.add(new MonData("2018-03-14 11:57:29", "-19.187", "0"));
        data.add(new MonData("2018-03-14 11:56:58", "-19.187", "0"));
        data.add(new MonData("2018-03-14 11:56:28", "-19.187", "0"));
        data.add(new MonData("2018-03-14 11:56:05", "-19.175", "0"));
        data.add(new MonData("2018-03-14 11:55:27", "-18.990", "0"));
        data.add(new MonData("2018-03-14 11:55:09", "-18.928", "0"));

        WarnListAdapter adapter = new WarnListAdapter(getActivity(), data);
        listView.setAdapter(adapter);
        return view;
    }

    private View thirdView(LayoutInflater inflater, ViewGroup container) {
        return null;
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

    private void initChart(LineChart chart) {
        //生成模拟数据
        //        for (int j = 0; j < 10; j++) {
        //            testData.add(new MonData(String.valueOf(year++),
        //                    String.valueOf(sensor.getYz() + (Math.random() - 0.7) * 100)));
        //        }
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
