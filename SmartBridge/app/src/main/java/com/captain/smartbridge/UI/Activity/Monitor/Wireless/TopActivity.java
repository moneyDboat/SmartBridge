package com.captain.smartbridge.UI.Activity.Monitor.Wireless;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.other.FourFraAdapter;
import com.captain.smartbridge.UI.Adapters.tian.TopFraAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by captain on 18-4-12.
 */

public class TopActivity extends AbsActivity {
    @BindView(R.id.top_toolbar)
    Toolbar toolbar;
    @BindView(R.id.top_tab)
    TabLayout topTab;
    @BindView(R.id.top_page)
    ViewPager topPage;

    TopFraAdapter adapter;
    String id = "";
    String sensors = "";

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_top);
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
        sensors = getIntent().getStringExtra("sensors");

        adapter = new TopFraAdapter(getSupportFragmentManager(), this, id, sensors);
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
