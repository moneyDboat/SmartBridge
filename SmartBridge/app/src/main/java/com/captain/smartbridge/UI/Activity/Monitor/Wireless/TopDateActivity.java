package com.captain.smartbridge.UI.Activity.Monitor.Wireless;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.tian.TopDataFraAdapter;
import com.captain.smartbridge.UI.Adapters.tian.TopFraAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by captain on 18-5-30.
 */

public class TopDateActivity extends AbsActivity {
    @BindView(R.id.topdata_toolbar)
    Toolbar toolbar;
    @BindView(R.id.topdata_tab)
    TabLayout topTab;
    @BindView(R.id.topdata_page)
    ViewPager topPage;

    TopDataFraAdapter adapter;
    String id = "";
    String sensor = "";

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_topdata);
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        id = getIntent().getStringExtra("id");
//        sensor = getIntent().getStringExtra("sensor");

        adapter = new TopDataFraAdapter(getSupportFragmentManager(), this, id, sensor);
        topPage.setAdapter(adapter);
        topTab.setupWithViewPager(topPage);
        topTab.setTabMode(TabLayout.MODE_FIXED);
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
