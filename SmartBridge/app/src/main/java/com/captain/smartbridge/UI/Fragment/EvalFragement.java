package com.captain.smartbridge.UI.Fragment;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.captain.smartbridge.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by Captain on 17/7/8.
 */

public class EvalFragement extends Fragment{
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ID = "";
    private  int mPage;
    private View view;

    private PieChart mChart;

    public static EvalFragement newInstance(int page){
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
        switch (mPage){
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

    private View gradeView(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.fragement_grade, container, false);

        mChart = (PieChart) view.findViewById(R.id.piechart);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);
//        mChart.setCenterTextTypeface(mTfLight);
//        mChart.setCenterText(generateCenterSpannableText());

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

        setData(3, 100);
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTextSize(12f);

        return view;

    }

    private void setData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count ; i++) {
//            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5),
//                    mParties[i % mParties.length],
//                    getResources().getDrawable(R.drawable.star)));
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5)));
        }

        //PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setDrawValues(false);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private View historyView(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.fragement_history, container, false);
        return view;
    }

    private View lifeView(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.fragement_life, container, false);
        return view;
    }
    private View degeView(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.fragement_dege, container, false);
        return view;
    }
}
