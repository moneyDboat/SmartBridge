package com.captain.smartbridge.UI.Activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.ListView;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Adapters.BridgeListAdapter;
import com.captain.smartbridge.model.MapReq;
import com.captain.smartbridge.model.MapRes;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by fish on 17-5-15.
 */

public class NearbyActivity extends AbsActivity {
    @BindView(R.id.nearby_toolbar)
    Toolbar toolbar;
    @BindView(R.id.list_bridge)
    ListView listView;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_nearby);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initList(){
        Intent intent = getIntent();
        String SF = intent.getStringExtra("SF");
        String CF = intent.getStringExtra("CF");
        if (NetUtils.isNetworkAvailable(this)) {
            MapReq mapReq = new MapReq(SF, CF);
            ApiManager.getmService().getMapInfo(mapReq).enqueue(new Callback<List<MapRes>>() {
                @Override
                public void onResponse(Call<List<MapRes>> call, Response<List<MapRes>> response) {
                    List<MapRes> bridges = response.body();
                    BridgeListAdapter listAdapter = new BridgeListAdapter(getApplicationContext(), bridges);
                    listView.addHeaderView(new ViewStub(getApplicationContext()));
                    listView.setAdapter(listAdapter);
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

}
