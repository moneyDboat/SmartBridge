package com.captain.smartbridge.UI.Activity.Monitor.Wireless;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.other.FourFraAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by captain on 18-4-12.
 */

public class ThingsActivity extends AbsActivity {
    @BindView(R.id.things_toolbar)
    Toolbar toolbar;
    @BindView(R.id.things_tab)
    TabLayout thingsTab;
    @BindView(R.id.things_page)
    ViewPager thingsPage;

    FourFraAdapter fourFraAdapter;

    String id = "";
    String sensor = "";

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_things);
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id = getIntent().getStringExtra("id");
        sensor = getIntent().getStringExtra("sensor");

        fourFraAdapter = new FourFraAdapter(getSupportFragmentManager(), this, id, sensor);
        thingsPage.setAdapter(fourFraAdapter);
        thingsTab.setupWithViewPager(thingsPage);
        thingsTab.setTabMode(TabLayout.MODE_FIXED);
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
