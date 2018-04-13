package com.captain.smartbridge.UI.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.Monitor.SensorAcitivty;
import com.captain.smartbridge.UI.Adapters.tian.MonitorAdapter;
import com.captain.smartbridge.model.MonBridge;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Captain on 17/8/21.
 */

public class MonFragement extends android.support.v4.app.Fragment{
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ID = "";
    private int mPage;
    private View view;

    public static MonFragement newInstance(int page){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MonFragement monFragement = new MonFragement();
        monFragement.setArguments(args);
        return monFragement;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {super.onCreate(savedInstanceState);
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

    private View firstView(LayoutInflater inflater, ViewGroup container){

        view = inflater.inflate(R.layout.fragment_firstmess, container, false);
        ListView list1 = (ListView) view.findViewById(R.id.firstmess_list);
        final List<MonBridge> mess1 = new ArrayList<>();

        final MonitorAdapter adapter1 = new MonitorAdapter(getActivity(), mess1);
        list1.setAdapter(adapter1);

        if (NetUtils.isNetworkAvailable(getActivity())){
            ApiManager.getmService().monBridges().enqueue(new Callback<List<MonBridge>>() {
                @Override
                public void onResponse(Call<List<MonBridge>> call, Response<List<MonBridge>> response) {
                    if(response.body() == null){
                        return;
                    }

                    List<MonBridge> bridges =  response.body();
                    for (MonBridge i : bridges){
                        if (i.getJclxmc().equals("结构性监测")){
                            mess1.add(i);
                        }
                    }

                    adapter1.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<MonBridge>> call, Throwable t) {

                }
            });
        }

        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SensorAcitivty.class);
                intent.putExtra("id", String.valueOf(mess1.get(position).getId()));
                startActivity(intent);
            }
        });

        return view;
    }

    private View secView(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.fragment_secmess, container, false);
        ListView list2 = (ListView) view.findViewById(R.id.secmess_list);
        final List<MonBridge> mess2 = new ArrayList<>();
        final MonitorAdapter adapter2 = new MonitorAdapter(getActivity(), mess2);
        list2.setAdapter(adapter2);

        if (NetUtils.isNetworkAvailable(getActivity())){
            ApiManager.getmService().monBridges().enqueue(new Callback<List<MonBridge>>() {
                @Override
                public void onResponse(Call<List<MonBridge>> call, Response<List<MonBridge>> response) {
                    if(response.body() == null){
                        return;
                    }

                    List<MonBridge> bridges =  response.body();
                    for (MonBridge i : bridges){
                        if (i.getJclxmc().equals("施工监测")){
                            mess2.add(i);
                        }
                    }

                    adapter2.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<MonBridge>> call, Throwable t) {

                }
            });
        }

        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SensorAcitivty.class);
                intent.putExtra("id", String.valueOf(mess2.get(position).getId()));
                startActivity(intent);
            }
        });

        return view;
    }

    private View thirdView(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.fragment_thirdmess, container, false);
        ListView list3 = (ListView) view.findViewById(R.id.thirdmess_list);
        final List<MonBridge> mess3 = new ArrayList<>();
        final MonitorAdapter adapter3 = new MonitorAdapter(getActivity(), mess3);
        list3.setAdapter(adapter3);

        if (NetUtils.isNetworkAvailable(getActivity())){
            ApiManager.getmService().monBridges().enqueue(new Callback<List<MonBridge>>() {
                @Override
                public void onResponse(Call<List<MonBridge>> call, Response<List<MonBridge>> response) {
                    if(response.body() == null){
                        return;
                    }

                    List<MonBridge> bridges =  response.body();
                    for (MonBridge i : bridges){
                        if (i.getJclxmc().equals("特殊监测")){
                            mess3.add(i);
                        }
                    }

                    adapter3.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<MonBridge>> call, Throwable t) {

                }
            });
        }

        list3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SensorAcitivty.class);
                intent.putExtra("id", String.valueOf(mess3.get(position).getId()));
                startActivity(intent);
            }
        });

        return view;
    }
}
