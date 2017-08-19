package com.captain.smartbridge.UI.Activity.Monitor;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Captain on 17/8/18.
 */

public class MonWarningActivity extends AbsActivity {
    @BindView(R.id.warning_toolbar)
    Toolbar toolbar;
    @BindView(R.id.warning_list)
    ListView warningList;
    @BindView(R.id.warning_swipe)
    SwipeRefreshLayout warningSwipe;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_monitor_warning);
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
        //获取预警数据
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
