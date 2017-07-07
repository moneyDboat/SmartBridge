package com.captain.smartbridge.UI.Activity.Monitor;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.other.MonitorAdapter;
import com.captain.smartbridge.model.other.Monitor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private void initList(){
        List<Monitor> missions = new ArrayList<>();
        missions.add(new Monitor("张营桥", "G00010003", "安徽省阜阳市颖泉区"));
        missions.add(new Monitor("南京长江大桥", "G00010004", "江苏省南京市浦口区"));

        MonitorAdapter adapter = new MonitorAdapter(MonMessActivity.this, missions);
        momessionList.setAdapter(adapter);

        momessionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                readyGo(SensorAcitivty.class);
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
