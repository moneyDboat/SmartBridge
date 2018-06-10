package com.captain.smartbridge.UI.Fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
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
import com.captain.smartbridge.UI.Activity.Monitor.Wireless.TopDateActivity;
import com.captain.smartbridge.UI.Adapters.SensorDataAdapter;
import com.captain.smartbridge.UI.Adapters.tian.SensorAdapter;
import com.captain.smartbridge.UI.Adapters.tian.WarnListAdapter;
import com.captain.smartbridge.UI.View.BatteryView;
import com.captain.smartbridge.model.other.MonData;
import com.captain.smartbridge.model.other.MonDataReq;
import com.captain.smartbridge.model.other.MonSensor;
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
        view = inflater.inflate(R.layout.fragment_sensor, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.frasensor_list);
        List<MonSensor> sensors = new ArrayList<>();

        //模拟传感器数据
        MonSensor sensor1 = new MonSensor();
        sensor1.setCgqmc("顶升传感器1");
        sensor1.setBswz("桥面");
        sensor1.setCgqlxmc("应变");
        sensor1.setCgqclmc("电磁式");
        MonSensor sensor2 = new MonSensor();
        sensor2.setCgqmc("顶升传感器2");
        sensor2.setBswz("桥面");
        sensor2.setCgqlxmc("应变");
        sensor2.setCgqclmc("电磁式");
        sensors.add(sensor1);
        sensors.add(sensor2);

        SensorAdapter adapter = new SensorAdapter(getActivity(), sensors);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), TopDateActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private View secView(LayoutInflater inflater, ViewGroup container) {
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
        final RelativeLayout linear4 = (RelativeLayout) view.findViewById(R.id.fra_top_lay4);
        final ImageView img4 = (ImageView) view.findViewById(R.id.fra_top_img4);
        final LinearLayout out4 = (LinearLayout) view.findViewById(R.id.fra_top_out4);


        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (out1.getVisibility() == View.VISIBLE) {
                    out1.setVisibility(View.GONE);
                    img1.setImageResource(R.drawable.down_white);
                } else {
                    out1.setVisibility(View.VISIBLE);
                    img1.setImageResource(R.drawable.right_white);
                }
                out2.setVisibility(View.GONE);
                img2.setImageResource(R.drawable.down_white);
                out3.setVisibility(View.GONE);
                img3.setImageResource(R.drawable.down_white);
                out4.setVisibility(View.GONE);
                img4.setImageResource(R.drawable.down_white);
            }
        });
        linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (out2.getVisibility() == View.VISIBLE) {
                    out2.setVisibility(View.GONE);
                    img2.setImageResource(R.drawable.down_white);
                } else {
                    out2.setVisibility(View.VISIBLE);
                    img2.setImageResource(R.drawable.right_white);
                }
                out1.setVisibility(View.GONE);
                img1.setImageResource(R.drawable.down_white);
                out3.setVisibility(View.GONE);
                img3.setImageResource(R.drawable.down_white);
                out4.setVisibility(View.GONE);
                img4.setImageResource(R.drawable.down_white);
            }
        });
        linear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (out3.getVisibility() == View.VISIBLE) {
                    out3.setVisibility(View.GONE);
                    img3.setImageResource(R.drawable.down_white);
                } else {
                    out3.setVisibility(View.VISIBLE);
                    img3.setImageResource(R.drawable.right_white);
                }
                out1.setVisibility(View.GONE);
                img1.setImageResource(R.drawable.down_white);
                out2.setVisibility(View.GONE);
                img2.setImageResource(R.drawable.down_white);
                out4.setVisibility(View.GONE);
                img4.setImageResource(R.drawable.down_white);
            }
        });
        linear4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (out4.getVisibility() == View.VISIBLE) {
                    out4.setVisibility(View.GONE);
                    img4.setImageResource(R.drawable.down_white);
                } else {
                    out4.setVisibility(View.VISIBLE);
                    img4.setImageResource(R.drawable.right_white);
                }
                out1.setVisibility(View.GONE);
                img1.setImageResource(R.drawable.down_white);
                out2.setVisibility(View.GONE);
                img2.setImageResource(R.drawable.down_white);
                out3.setVisibility(View.GONE);
                img3.setImageResource(R.drawable.down_white);
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
}
