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
import com.captain.smartbridge.model.MonBridge;
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
        if (NetUtils.isNetworkAvailable(this)) {
            ApiManager.getmService().monBridges().enqueue(new Callback<List<MonBridge>>() {
                @Override
                public void onResponse(Call<List<MonBridge>> call, Response<List<MonBridge>> response) {
                    if (response.body() == null) {
                        showToast("账户登录过期，请退出账户后重新登录");
                        return;
                    }

                    List<MonBridge> bridges = response.body();
                    missions = new ArrayList<>();
                    for (MonBridge i : bridges) {
                        missions.add(new Monitor(i.getQlmc(), i.getQldm(), i.getSf() + i.getSc() + i.getQx()));
                    }

                    MonitorAdapter adapter = new MonitorAdapter(MonMessActivity.this, missions);
                    momessionList.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<List<MonBridge>> call, Throwable t) {
                    t.printStackTrace();
                    showToast("网络错误");
                }
            });
        } else {
            showToast("请检查您的网络");
        }


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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
