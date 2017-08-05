package com.captain.smartbridge.UI.Activity.Monitor;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.other.MonitorAdapter;
import com.captain.smartbridge.model.MapReq;
import com.captain.smartbridge.model.MapRes;
import com.captain.smartbridge.model.other.Monitor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Captain on 17/7/4.
 */

public class MonMessActivity extends AbsActivity {
    @BindView(R.id.momession_toolbar)
    Toolbar toolbar;
    @BindView(R.id.momession_list)
    ListView momessionList;
    @BindView(R.id.momession_swipe)
    SwipeRefreshLayout momessionSwipe;

    List<Monitor> missions = new ArrayList<>();

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_momession);
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
        String SF = "江苏省";
        String CF = "南京市";
        if (NetUtils.isNetworkAvailable(this)) {
            MapReq mapReq = new MapReq(SF, CF);
            ApiManager.getmService().getMapInfo(mapReq).enqueue(new Callback<List<MapRes>>() {
                @Override
                public void onResponse(Call<List<MapRes>> call, Response<List<MapRes>> response) {
                    List<MapRes> bridges = response.body();
                    missions = new ArrayList<>();
                    for (MapRes i : bridges) {
                        missions.add(new Monitor(i.getQlmc(), i.getQldm(), i.getSf() + i.getCs() + i.getQx()));
                    }

                    MonitorAdapter adapter = new MonitorAdapter(MonMessActivity.this, missions);
                    momessionList.setAdapter(adapter);

                    momessionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(MonMessActivity.this, SensorAcitivty.class);
                            intent.putExtra("bridge", missions.get(position).getCode());
                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void onFailure(Call<List<MapRes>> call, Throwable t) {
                    t.printStackTrace();
                    showToast("网络错误");
                }
            });
        } else {
            showToast("请检查您的网络");
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
}
