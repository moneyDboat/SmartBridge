package com.captain.smartbridge.UI.Activity.Evalute;

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
import com.captain.smartbridge.model.other.EvaluteMess;
import com.captain.smartbridge.model.other.Monitor;
import com.google.gson.Gson;

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

public class EvMessActivity extends AbsActivity {
    @BindView(R.id.evmession_toolbar)
    Toolbar toolbar;
    @BindView(R.id.evmession_list)
    ListView evmessionList;
    @BindView(R.id.evmession_swipe)
    SwipeRefreshLayout evmessionSwipe;

    List<EvaluteMess> evalutions = new ArrayList<>();


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
        if (NetUtils.isNetworkConnected(this)){
            ApiManager.getmService().getEvaMess().enqueue(new Callback<List<EvaluteMess>>() {
                @Override
                public void onResponse(Call<List<EvaluteMess>> call, Response<List<EvaluteMess>> response) {
                    evalutions = response.body();

                    List<Monitor> monitors = new ArrayList<>();
                    for (EvaluteMess i :evalutions){
                        monitors.add(new Monitor(i.getQlmc(), i.getScore().substring(0, 6), i.getQlwz()));
                    }
                    MonitorAdapter adapter = new MonitorAdapter(EvMessActivity.this, monitors);
                    evmessionList.setAdapter(adapter);
                    evmessionSwipe.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<List<EvaluteMess>> call, Throwable t) {
                    t.printStackTrace();
                    evmessionSwipe.setRefreshing(false);
                    showToast("网络错误");
                }
            });
        }else{
            evmessionSwipe.setRefreshing(false);
            showNetWorkError();
        }

        evmessionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EvMessActivity.this, EvalInfoActivity.class);
                intent.putExtra("eval", new Gson().toJson(evalutions.get(position)));
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
