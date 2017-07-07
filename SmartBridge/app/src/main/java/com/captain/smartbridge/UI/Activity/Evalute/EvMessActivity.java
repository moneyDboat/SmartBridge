package com.captain.smartbridge.UI.Activity.Evalute;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.other.MonitorAdapter;
import com.captain.smartbridge.model.other.Evalution;
import com.captain.smartbridge.model.other.Monitor;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Captain on 17/7/4.
 */

public class EvMessActivity extends AbsActivity {
    @BindView(R.id.evmession_toolbar)
    Toolbar toolbar;
    @BindView(R.id.evmession_list)
    ListView evmessionList;
    @BindView(R.id.evmession_swipe)
    SwipeRefreshLayout evmessionSwipe;

    List<Evalution> evalutions = new ArrayList<>();


    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_evmession);
    }

    @Override
    protected void prepareDatas() {
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initList();
    }

    private void initList() {
        getData();

        List<Monitor> monitors = new ArrayList<>();
        for (Evalution i :evalutions){
            monitors.add(new Monitor(i.getName(), i.getGrade(), i.getLocation()));
        }

        MonitorAdapter adapter = new MonitorAdapter(this, monitors);
        evmessionList.setAdapter(adapter);

        evmessionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EvMessActivity.this, EvalInfoActivity.class);
                intent.putExtra("eval", new Gson().toJson(evalutions.get(position)));
                startActivity(intent);
            }
        });
    }

    private void getData() {
        evalutions.add(new Evalution("张营桥", "拱桥", "安徽省阜阳市", "无", "system",
                "计算机科学与工程学院", "2017-06-01", "93.644", "2"));
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
