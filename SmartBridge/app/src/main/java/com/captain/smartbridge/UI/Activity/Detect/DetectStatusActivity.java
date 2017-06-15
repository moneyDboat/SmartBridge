package com.captain.smartbridge.UI.Activity.Detect;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.DeMissionListAdapter;
import com.captain.smartbridge.model.Mission;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetectStatusActivity extends AbsActivity implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.destatus_toolbar)
    Toolbar toolbar;
    @BindView(R.id.destatus_list)
    ListView listView;
    @BindView(R.id.destatus_swipe)
    SwipeRefreshLayout destatusSwipe;

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

        List<Mission> missions = new ArrayList<>();
        missions.add(new Mission("南京长江大桥", "G1001", "监管单位", "检测单位", 0));
        missions.add(new Mission("南京第二大桥", "G1001", "监管单位", "检测单位", 0));
        missions.add(new Mission("南京第三大桥", "G1001", "监管单位", "检测单位", 0));
        missions.add(new Mission("南京第四大桥", "G1001", "监管单位", "检测单位", 0));
        missions.add(new Mission("南京第五大桥", "G1001", "监管单位", "检测单位", 0));
        DeMissionListAdapter adapter = new DeMissionListAdapter(this, missions);
        listView.setHeaderDividersEnabled(true);
        listView.setAdapter(adapter);
    }

    //获取检测状态
    private void getMission(){

    }

    @Override
    public void onRefresh() {
        //
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
