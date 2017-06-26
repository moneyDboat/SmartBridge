package com.captain.smartbridge.UI.Activity.Detect;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.DeMissionListAdapter;
import com.captain.smartbridge.model.DetectMission;
import com.captain.smartbridge.model.Mission;
import com.captain.smartbridge.model.SearchCodeReq;
import com.captain.smartbridge.model.SearchCodeRes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.captain.smartbridge.API.ApiManager.getmService;

public class DetectStatusActivity extends AbsActivity implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.destatus_toolbar)
    Toolbar toolbar;
    @BindView(R.id.destatus_list)
    ListView listView;
    @BindView(R.id.destatus_swipe)
    SwipeRefreshLayout destatusSwipe;

    List<Mission> missions = new ArrayList<>();
    String name = "";
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

        //获取检测任务
        initList();
        adapter = new DeMissionListAdapter(DetectStatusActivity.this, missions);
        listView.setHeaderDividersEnabled(true);
        listView.setAdapter(adapter);
    }

    //获取检测任务
    private void initList(){
        if(NetUtils.isNetworkConnected(this)){
            getmService().getDetect().enqueue(new Callback<List<DetectMission>>() {
                @Override
                public void onResponse(Call<List<DetectMission>> call, Response<List<DetectMission>> response) {
                    missions = new ArrayList<>();
                    for(DetectMission i : response.body()){
                        //状态为3代表已经完成
                        if (i.getStatus().equals("2")){
                            continue;
                        }
                        getBridgeName(i.getQldm(), i);
                    }
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

    private String getBridgeName(final String code, DetectMission mission){
        SearchCodeReq searchCodeReq = new SearchCodeReq(code);
        final DetectMission i = mission;
        ApiManager.getmService().search(searchCodeReq).enqueue(new Callback<List<SearchCodeRes>>() {
            @Override
            public void onResponse(Call<List<SearchCodeRes>> call, Response<List<SearchCodeRes>> response) {
                if(response.body() == null){
                    Log.e("name", code);
                }else{
                    name = response.body().get(0).getQlmc();
                    Mission mission = new Mission(name, i.getQldm(), i.getRwfbry(), i.getRwjsry(), Integer.valueOf(i.getStatus()));
                    missions.add(mission);

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<SearchCodeRes>> call, Throwable t) {
                t.printStackTrace();
                showNetWorkError();
            }
        });

        return name;
    }

    @Override
    public void onRefresh() {
        initList();
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
