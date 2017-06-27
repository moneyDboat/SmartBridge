package com.captain.smartbridge.UI.Activity.Detect;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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

public class DetectStatusActivity extends AbsActivity implements SwipeRefreshLayout.OnRefreshListener,
        AdapterView.OnItemClickListener{
    @BindView(R.id.destatus_toolbar)
    Toolbar toolbar;
    @BindView(R.id.destatus_list)
    ListView listView;
    @BindView(R.id.destatus_swipe)
    SwipeRefreshLayout destatusSwipe;

    List<Mission> missions = new ArrayList<>();
    List<DetectMission> detectMissions = new ArrayList<>();
    DeMissionListAdapter adapter = null;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_destatus);
    }

    @Override
    protected void prepareDatas() {
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //pull to refresh
        destatusSwipe.setOnRefreshListener(this);

       //list
        listView.setOnItemClickListener(this);

        //获取未完成的检测任务
        initList();
    }

    //获取检测任务
    private void initList(){
        if(NetUtils.isNetworkConnected(this)){
            getmService().getDetect().enqueue(new Callback<List<DetectMission>>() {
                @Override
                public void onResponse(Call<List<DetectMission>> call, Response<List<DetectMission>> response) {
                    missions = new ArrayList<>();
                    for(DetectMission i :  response.body()){
                        //状态为2代表已经完成
                        if (i.getStatus().equals("2")){
                            continue;
                        }

                        detectMissions.add(i);
                        Mission mission = new Mission(i.getQlmc(),i.getQldm(), i.getRwfbry(),
                                i.getRwjsry(), Integer.valueOf(i.getStatus()));
                        missions.add(mission);
                    }

                    adapter = new DeMissionListAdapter(DetectStatusActivity.this, missions);
                    listView.setAdapter(adapter);
                    destatusSwipe.setRefreshing(false);
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
                return true;
            case R.id.destatus_create:
                readyGo(DeCreateActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_destatus, menu);
        return true;
    }
}
