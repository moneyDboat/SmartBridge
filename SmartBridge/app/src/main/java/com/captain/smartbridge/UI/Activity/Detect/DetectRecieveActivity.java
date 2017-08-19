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

public class DetectRecieveActivity extends AbsActivity implements SwipeRefreshLayout.OnRefreshListener,
        AdapterView.OnItemClickListener {
    @BindView(R.id.dereceive_toolbar)
    Toolbar toolbar;
    @BindView(R.id.dereceive_list)
    ListView listView;
    @BindView(R.id.dereceive_swipe)
    SwipeRefreshLayout dereceiveSwipe;

    List<Mission> missions = new ArrayList<>();
    List<DetectMission> detectMissions = new ArrayList<>();
    DeMissionListAdapter adapter = null;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_derecieve);
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

        listView.setOnItemClickListener(this);
        dereceiveSwipe.setOnRefreshListener(this);

    }

    private void initList() {
        if (NetUtils.isNetworkConnected(this)) {
            getmService().getDetect().enqueue(new Callback<List<DetectMission>>() {
                @Override
                public void onResponse(Call<List<DetectMission>> call, Response<List<DetectMission>> response) {
                    if (response.body() == null) {
                        showToast("账户登录过期，请退出账户后重新登录");
                        return;
                    }

                    missions = new ArrayList<>();
                    for (DetectMission i : response.body()) {
                        //状态为0代表待接收
                        if (i.getStatus().equals("0")) {
                            detectMissions.add(i);
                            Mission mission = new Mission(i.getQlmc(), i.getQldm(), i.getRwfbry(),
                                    i.getRwjsry(), Integer.valueOf(i.getStatus()));
                            missions.add(mission);
                        }
                    }

                    adapter = new DeMissionListAdapter(DetectRecieveActivity.this, missions);
                    listView.setAdapter(adapter);
                    dereceiveSwipe.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<List<DetectMission>> call, Throwable t) {
                    t.printStackTrace();
                    dereceiveSwipe.setRefreshing(false);
                    showNetWorkError();
                }
            });
        } else {
            dereceiveSwipe.setRefreshing(false);
            showNetWorkError();
        }

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

    @Override
    public void onRefresh() {
        initList();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DetectRecieveInfoActivity.class);
        intent.putExtra("detect", new Gson().toJson(detectMissions.get(position)));
        startActivity(intent);
    }
}
