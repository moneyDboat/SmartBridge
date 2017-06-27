package com.captain.smartbridge.UI.Activity.Detect;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.DeMissionListAdapter;
import com.captain.smartbridge.model.DetectMission;
import com.captain.smartbridge.model.Mission;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.captain.smartbridge.API.ApiManager.getmService;

/**
 * Created by fish on 17-5-17.
 */

public class DetectSummaryActivity extends AbsActivity implements SwipeRefreshLayout.OnRefreshListener,
        AdapterView.OnItemClickListener{
    @BindView(R.id.desummary_toolbar)
    Toolbar toolbar;
    @BindView(R.id.desummary_list)
    ListView listView;
    @BindView(R.id.desummary_swipe)
    SwipeRefreshLayout desummarySwipe;

    List<Mission> missions = new ArrayList<>();
    DeMissionListAdapter adapter = null;
    List<DetectMission> detectMissions = new ArrayList<>();


    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_desummary);
    }

    @Override
    protected void prepareDatas() {
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        desummarySwipe.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);

        //获取已完成的任务
        initList();
    }

    private void initList() {
        if(NetUtils.isNetworkConnected(this)){
            getmService().getDetect().enqueue(new Callback<List<DetectMission>>() {
                @Override
                public void onResponse(Call<List<DetectMission>> call, Response<List<DetectMission>> response) {
                    missions = new ArrayList<>();
                    for(DetectMission i : response.body()){
                        //状态为2代表已经完成
                        if (i.getStatus().equals("2")){
                            Mission mission = new Mission(i.getQlmc(),i.getQldm(), i.getRwfbry(),
                                    i.getRwjsry(), Integer.valueOf(i.getStatus()));
                            missions.add(mission);
                            detectMissions.add(i);
                        }
                    }

                    adapter = new DeMissionListAdapter(DetectSummaryActivity.this, missions);
                    listView.setAdapter(adapter);
                    desummarySwipe.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<List<DetectMission>> call, Throwable t) {
                    t.printStackTrace();
                    showNetWorkError();
                }
            });
        }else {
            showNetWorkError();
        }
    }

    @Override
    public void onRefresh() {
        initList();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DetectInfoActivity.class);
        intent.putExtra("detect", new Gson().toJson(detectMissions.get(position)));
        startActivity(intent);
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
