package com.captain.smartbridge.UI.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ListView;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.BaseApplication;
import com.captain.smartbridge.UI.Activity.WebActivity;
import com.captain.smartbridge.UI.Adapters.TextListAdapter;
import com.captain.smartbridge.UI.Adapters.TextsListAdapter;
import com.captain.smartbridge.model.GouJian;
import com.captain.smartbridge.model.SearchCodeReq;
import com.captain.smartbridge.model.SearchCodeRes;
import com.captain.smartbridge.model.SimpleText;
import com.captain.smartbridge.model.SimpleTexts;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by fish on 17-5-15.
 */

public class PageFragement extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ID = "";
    private int mPage;
    private View view;

    public static PageFragement newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragement pageFragement = new PageFragement();
        pageFragement.setArguments(args);
        return pageFragement;
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
                return baseInforView(inflater, container);
            case 1:
                return buildView(inflater, container);
            case 2:
                return pictureView(inflater, container);
            case 3:
                return webView(inflater, container);
            default:
                return null;
        }
    }

    private View webView(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.fragment_web, container, false);
        Button button = (Button)view.findViewById(R.id.fragment_web_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private View baseInforView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_baseinfor, container, false);

        String ID = BaseApplication.getID();
        SearchCodeReq searchCodeReq = new SearchCodeReq(ID);

        if (NetUtils.isNetworkConnected(getActivity())) {
            ApiManager.getmService().search(searchCodeReq).enqueue(new Callback<List<SearchCodeRes>>() {
                @Override
                public void onResponse(Call<List<SearchCodeRes>> call, Response<List<SearchCodeRes>> response) {
                    if (response.body() == null) {
                        return;
                    }

                    SearchCodeRes bridge = response.body().get(0);

                    List<SimpleText> texts = new ArrayList<>();
                    texts.add(new SimpleText("桥梁代码", bridge.getQldm()));
                    texts.add(new SimpleText("桥梁名称", bridge.getQlmc()));
                    texts.add(new SimpleText("桥梁分类", "特大桥"));
                    texts.add(new SimpleText("桥梁类型", "梁式桥"));
                    texts.add(new SimpleText("路线号", "G1001"));
                    texts.add(new SimpleText("桥梁位置", bridge.getSf() + bridge.getCs() + bridge.getQx()));
                    texts.add(new SimpleText("幅度", "单幅"));
                    texts.add(new SimpleText("桥梁全长", "12004m"));
                    texts.add(new SimpleText("建桥时间", bridge.getJqsj()));
                    texts.add(new SimpleText("设计时间", bridge.getSjsj()));
                    texts.add(new SimpleText("备注", " "));

                    TextListAdapter listAdapter = new TextListAdapter(getActivity(), texts);
                    ListView listView = (ListView) view.findViewById(R.id.Base_infor_listview);
                    listView.addHeaderView(new ViewStub(getActivity()));
                    listView.setAdapter(listAdapter);
                }

                @Override
                public void onFailure(Call<List<SearchCodeRes>> call, Throwable t) {

                }
            });
        } else {

        }
        return view;
    }

    private View buildView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_build, container, false);

        String id = BaseApplication.getID();
        SearchCodeReq searchCodeReq = new SearchCodeReq(id);

        if (NetUtils.isNetworkConnected(getActivity())) {
            ApiManager.getmService().getGou(searchCodeReq).enqueue(new Callback<List<GouJian>>() {
                @Override
                public void onResponse(Call<List<GouJian>> call, Response<List<GouJian>> response) {
                    if (response.body() == null) {
                        return;
                    }

                    List<GouJian> gous = response.body();
                    List<SimpleTexts> texts = new ArrayList<>();
                    for (GouJian gou : gous) {
                        texts.add(new SimpleTexts(gou.getGjmc(), gou.getGjlxmc(),
                                String.valueOf(gou.getGjsl()), gou.getGjjgm()));
                    }

                    TextsListAdapter listAdapter = new TextsListAdapter(getActivity(), texts);
                    ListView listView = (ListView) view.findViewById(R.id.build_list);
                    listView.addHeaderView(new ViewStub(getActivity()));
                    listView.setAdapter(listAdapter);

                }

                @Override
                public void onFailure(Call<List<GouJian>> call, Throwable t) {

                }
            });
        } else {

        }
        return view;
    }

    private View pictureView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_picture, container, false);
    }
}
