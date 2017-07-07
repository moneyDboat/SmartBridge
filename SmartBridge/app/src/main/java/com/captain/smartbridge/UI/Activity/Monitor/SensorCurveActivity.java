package com.captain.smartbridge.UI.Activity.Monitor;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.TextListAdapter;
import com.captain.smartbridge.model.SimpleText;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        setTitle("应力传感器1号");
        initChart();
        initList();
    }

    private void initChart(){
        List<Entry> entries = new ArrayList<>();
        Random random = new Random();
        for (int i =0;i<20;i++){
            entries.add(new Entry(i, random.nextInt(10)));
        }

        LineDataSet dataSet = new LineDataSet(entries, "传感器数值");
        //importance!!!
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setCubicIntensity(0.2f);
        dataSet.setLineWidth(1.8f);
        dataSet.setColor(Color.WHITE);
        dataSet.setFillColor(Color.WHITE);
        dataSet.setFillAlpha(100);

        LineData lineData = new LineData(dataSet);

        chart.getDescription().setEnabled(false);
        chart.setData(lineData);
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
//        chart.notifyDataSetChanged();
    }

    private void initList(){
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
