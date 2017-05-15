package com.captain.smartbridge.UI.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ListView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Adapters.TextListAdapter;
import com.captain.smartbridge.UI.Adapters.TextsListAdapter;
import com.captain.smartbridge.model.SimpleText;
import com.captain.smartbridge.model.SimpleTexts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fish on 17-5-15.
 */

public class PageFragement extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private  int mPage;
    private View view;

    public static PageFragement newInstance(int page){
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
        switch (mPage){
            case 0:
                return baseInforView(inflater, container);
            case 1:
                return buildView(inflater, container);
            case 2:
                return pictureView(inflater, container);
            default:
                return null;
        }
    }

    private View baseInforView(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.fragment_baseinfor, container, false);

        List<SimpleText> texts = new ArrayList<>();
        texts.add(new SimpleText("桥梁代码", "G10011004"));
        texts.add(new SimpleText("桥梁名称", "南京长江大桥"));
        texts.add(new SimpleText("桥梁分类", "特大桥"));
        texts.add(new SimpleText("桥梁类型", "梁式桥"));
        texts.add(new SimpleText("路线号", "G1001"));
        texts.add(new SimpleText("桥梁位置", "江苏省南京市"));
        texts.add(new SimpleText("幅度", "单幅"));
        texts.add(new SimpleText("桥梁全长", "12004m"));
        texts.add(new SimpleText("建桥时间", "2011-04-04"));
        texts.add(new SimpleText("设计时间", "1009-05-16"));
        texts.add(new SimpleText("备注", " "));

        TextListAdapter listAdapter = new TextListAdapter(this.getContext(), texts);
        ListView listView = (ListView) view.findViewById(R.id.Base_infor_listview);
        listView.addHeaderView(new ViewStub(this.getContext()));
        listView.setAdapter(listAdapter);

        return view;
    }

    private View buildView(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.fragment_build, container, false);

        List<SimpleTexts> texts = new ArrayList<>();
        texts.add(new SimpleTexts("护坡","锥坡","2","下部结构"));
        texts.add(new SimpleTexts("台帽","桥台","2","下部结构"));
        texts.add(new SimpleTexts("耳墙","耳墙","2","下部结构"));
        texts.add(new SimpleTexts("盖梁","桥墩","1","下部结构"));
        texts.add(new SimpleTexts("墩柱","桥墩","2","下部结构"));

        TextsListAdapter listAdapter = new TextsListAdapter(this.getContext(), texts);
        ListView listView = (ListView) view.findViewById(R.id.build_list);
        listView.addHeaderView(new ViewStub(this.getContext()));
        listView.setAdapter(listAdapter);
        return view;
    }

    private View pictureView(LayoutInflater inflater, ViewGroup container){
        return inflater.inflate(R.layout.fragment_picture, container, false);
    }
}
