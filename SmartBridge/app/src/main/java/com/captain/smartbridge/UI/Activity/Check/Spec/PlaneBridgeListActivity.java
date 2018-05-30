package com.captain.smartbridge.UI.Activity.Check.Spec;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Activity.Monitor.MonitorActivity;
import com.captain.smartbridge.UI.Adapters.tian.MonitorAdapter;
import com.captain.smartbridge.model.MonBridge;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by captain on 18-5-30.
 */

public class PlaneBridgeListActivity extends AbsActivity{
    @BindView(R.id.planebridgelist_toolbar)
    Toolbar toolbar;
    @BindView(R.id.planebridgelist_list)
    ListView bridgeList;

    List<MonBridge> bridges = new ArrayList<>();
    MonitorAdapter adapter = new MonitorAdapter(this, bridges);

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_planebridgelist);
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bridgeList.setAdapter(adapter);

        if (NetUtils.isNetworkAvailable(this)) {
            ApiManager.getmService().monBridges().enqueue(new Callback<List<MonBridge>>() {
                @Override
                public void onResponse(Call<List<MonBridge>> call, Response<List<MonBridge>> response) {
                    if (response.body() == null) {
                        return;
                    }

                    List<MonBridge> data = response.body();
                    for (MonBridge da : data) {
                        bridges.add(da);
                    }

                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<MonBridge>> call, Throwable t) {
                    t.printStackTrace();
                    showNetWorkError();
                }
            });
        }

        bridgeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplication(), PlaneActivity.class);
                intent.putExtra("id", String.valueOf(bridges.get(position).getId()));
                intent.putExtra("bridge", String.valueOf(bridges.get(position).getQlmc()));
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


