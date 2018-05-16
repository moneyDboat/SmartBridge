package com.captain.smartbridge.UI.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.Check.Spec.FlyActivity;
import com.captain.smartbridge.UI.Adapters.other.PlaneListAdapter;
import com.captain.smartbridge.model.Plane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by captain on 18-5-15.
 */

public class PlaneFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ID = "";
    private int mPage;
    private View view;

    public static PlaneFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PlaneFragment fragment = new PlaneFragment();
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
            default:
                return null;
        }
    }

    private View firstView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_plane1, container, false);
        return view;
    }

    private View secView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_plane2, container, false);
        ListView listView = (ListView) view.findViewById(R.id.plane2_list);

        //模拟数据
        List<Plane> planes = new ArrayList<>();
        planes.add(new Plane("第一次飞行", "2017-04-20"));
        planes.add(new Plane("第二次飞行", "2017-06-05"));
        planes.add(new Plane("第三次飞行", "2017-10-13"));
        planes.add(new Plane("第四次飞行", "2017-10-13"));
        planes.add(new Plane("第五次飞行", "2017-10-16"));
        planes.add(new Plane("第六次飞行", "2017-10-16"));
        planes.add(new Plane("第七次飞行", "2017-10-16"));
        planes.add(new Plane("第八次飞行", "2017-11-20"));
        planes.add(new Plane("第九次飞行", "2017-11-23"));
        planes.add(new Plane("第十次飞行", "2017-12-17"));
        PlaneListAdapter adapter = new PlaneListAdapter(getActivity(), planes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), FlyActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
