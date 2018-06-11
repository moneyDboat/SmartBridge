package com.captain.smartbridge.UI.Activity.Check.Spec.List;

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
import com.captain.smartbridge.UI.Activity.Check.Spec.PlaneActivity;
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
 * Created by captain on 18-6-11.
 */

public class SpecBriActivity extends AbsActivity {
    @BindView(R.id.specbridgelist_toolbar)
    Toolbar toolbar;
    @BindView(R.id.specbridgelist_list)
    ListView bridgeList;

    List<MonBridge> bridges = new ArrayList<>();
    MonitorAdapter adapter = new MonitorAdapter(this, bridges);
    //0代表水下，1代表无人机
    int type = 0;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_specbrilist);
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        type = getIntent().getIntExtra("type", 0);

        bridgeList.setAdapter(adapter);

        if (NetUtils.isNetworkAvailable(this)) {
            ApiManager.getmService().monwrjBridges().enqueue(new Callback<List<MonBridge>>() {
                @Override
                public void onResponse(Call<List<MonBridge>> call, Response<List<MonBridge>> response) {
                    if (response.body() == null) {
                        return;
                    }

                    List<MonBridge> data = response.body();
                    if (type == 0){
                        for (MonBridge da : data){
                            if (da.getJclxmc().equals("水下检测")){
                                bridges.add(da);
                            }
                        }
                    }else {
                        for (MonBridge da : data){
                            if (da.getJclxmc().equals("无人机检测")){
                                bridges.add(da);
                            }
                        }
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
                Intent intent;
                if (type == 0){
                    intent = new Intent(getApplication(), WaSenActivity.class);
                }else {
                    intent = new Intent(getApplication(), FlySenActivity.class);
                }
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
