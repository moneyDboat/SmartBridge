package com.captain.smartbridge.UI.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.BaseApplication;
import com.captain.smartbridge.model.SearchCodeReq;
import com.captain.smartbridge.model.other.EvaGrade;
import com.captain.smartbridge.model.other.EvaHistory;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Captain on 17/7/8.
 */

public class EvalFragement extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ID = "";
    private int mPage;
    private View view;

    private PieChart mChart;
    private BarChart bChart;

    List<String> dates = new ArrayList<>();

    public static EvalFragement newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        EvalFragement fragement = new EvalFragement();
        fragement.setArguments(args);
        return fragement;
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
                return gradeView(inflater, container);
            case 1:
                return historyView(inflater, container);
            case 2:
                return lifeView(inflater, container);
            case 3:
                return degeView(inflater, container);
            default:
                return null;
        }
    }

    private View gradeView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragement_grade, container, false);

        if (NetUtils.isNetworkConnected(getActivity())) {
            SearchCodeReq req = new SearchCodeReq(BaseApplication.getEVAID());
            ApiManager.getmService().getEvaGrade(req).enqueue(new Callback<EvaGrade>() {
                @Override
                public void onResponse(Call<EvaGrade> call, Response<EvaGrade> response) {
                    setPieChart(view, response.body());
                }

                @Override
                public void onFailure(Call<EvaGrade> call, Throwable t) {

                }
            });
        }

        return view;
    }

    private View historyView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragement_history, container, false);

        if (NetUtils.isNetworkConnected(getActivity())) {
            SearchCodeReq req = new SearchCodeReq(BaseApplication.getEVAID());
            ApiManager.getmService().getEvaHistory(req).enqueue(new Callback<List<EvaHistory>>() {
                @Override
                public void onResponse(Call<List<EvaHistory>> call, Response<List<EvaHistory>> response) {
                    setBarChart(view, response.body());
                }

                @Override
                public void onFailure(Call<List<EvaHistory>> call, Throwable t) {

                }
            });
        }
        return view;
    }

    private View lifeView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragement_life, container, false);
        return view;
    }

    private View degeView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragement_dege, container, false);
        return view;
    }

    //设置饼图
    private void setPieChart(View view, EvaGrade grade) {
        mChart = (PieChart) view.findViewById(R.id.piechart);
        mChart.setUsePercentValues(false);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);
        mChart.setDrawCenterText(true);
        mChart.setRotationAngle(0);

        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        setPieData(grade);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);
        // 动画效果

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTextSize(12f);
    }


    private void setPieData(EvaGrade grade) {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        entries.add(new PieEntry((float) grade.getTop_score(), "上部结构"));
        entries.add(new PieEntry((float) grade.getDeck_score(), "桥面结构"));
        entries.add(new PieEntry((float) grade.getBottom_score(), "下部结构"));

        PieDataSet dataSet = new PieDataSet(entries, "桥梁各结构分数");
        dataSet.setDrawValues(true);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        // data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private void setBarChart(View view, List<EvaHistory> grades) {
        bChart = (BarChart) view.findViewById(R.id.barchart);
        bChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        bChart.setMaxVisibleValueCount(40);

        // scaling can now only be done on x- and y-axis separately
        bChart.setTouchEnabled(false);
        bChart.setClickable(false);
        bChart.setPinchZoom(false);

        bChart.setDrawGridBackground(false);
        bChart.setDrawBarShadow(false);

        bChart.setDrawValueAboveBar(false);
        bChart.setHighlightFullBarEnabled(false);

        // change the position of the y-labels
        YAxis leftAxis = bChart.getAxisLeft();
        // leftAxis.setValueFormatter(new MyAxisValueFormatter());
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        bChart.getAxisRight().setEnabled(false);

        XAxis xLabels = bChart.getXAxis();
        xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);
        //将X轴值设为日期
        for(EvaHistory grade:grades){
            dates.add(grade.getLrsj());
        }
        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return dates.get((int)value);
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        };
        xLabels.setValueFormatter(formatter);
        xLabels.setGranularity(1f);
        xLabels.setLabelRotationAngle(-30);


        setBarData(grades);
        bChart.animateY(3000);

        Legend l = bChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);
    }

    private void setBarData(List<EvaHistory> grades) {
        ArrayList<BarEntry> yVals = new ArrayList<>();
        int i = 0;
        for (EvaHistory eva : grades) {
            yVals.add(new BarEntry(i, new float[]{(float) eva.getTop_score(),
                    (float) eva.getDeck_score(), (float) eva.getBottom_score()}, eva.getLrsj()));
            i++;
        }

        BarDataSet dataSet = new BarDataSet(yVals, "历史评估数据");
        dataSet.setStackLabels(new String[]{"上部结构", "桥面结构", "下部结构"});
        dataSet.setColors(getColors());

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        BarData data = new BarData(dataSets);
        data.setValueTextColor(Color.WHITE);

        bChart.setData(data);
    }

    private int[] getColors(){
        int stacksize = 3;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        for (int i = 0; i < colors.length; i++) {
            colors[i] = ColorTemplate.PASTEL_COLORS[i];
        }

        return colors;
    }
}
