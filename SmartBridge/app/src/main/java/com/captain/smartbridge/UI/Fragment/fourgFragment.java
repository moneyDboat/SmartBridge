package com.captain.smartbridge.UI.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Adapters.tian.WarnListAdapter;
import com.captain.smartbridge.UI.View.BatteryView;
import com.captain.smartbridge.model.other.MonData;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by captain on 18-3-23.
 */

public class fourgFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ID = "";
    private int mPage;
    private View view;

    //是否是4G页面
    private Boolean if4g = false;

    public static fourgFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fourgFragment fragment = new fourgFragment();
        fragment.setArguments(args);
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
        LineChart chart = (LineChart) view.findViewById(R.id.four1_chart);
        ListView listView = (ListView) view.findViewById(R.id.four1_list);

        //创建虚拟数据
        List<MonData> data = new ArrayList<>();
        data.add(new MonData("2018-03-14 11:58:31","-19.187","0"));
        data.add(new MonData("2018-03-14 11:58:01","-19.187","0"));
        data.add(new MonData("2018-03-14 11:57:29","-19.187","0"));
        data.add(new MonData("2018-03-14 11:56:58","-19.187","0"));
        data.add(new MonData("2018-03-14 11:56:28","-19.187","0"));
        data.add(new MonData("2018-03-14 11:56:05","-19.175","0"));
        data.add(new MonData("2018-03-14 11:55:27","-18.990","0"));
        data.add(new MonData("2018-03-14 11:55:09","-18.928","0"));

        WarnListAdapter adapter = new WarnListAdapter(getActivity(), data);
        listView.setAdapter(adapter);

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
}
