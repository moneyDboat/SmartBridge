package com.captain.smartbridge.UI.Activity.Monitor.Wireless;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.tian.PicFraAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by captain on 18-4-12.
 */

public class PicActivity extends AbsActivity {
    @BindView(R.id.pic_toolbar)
    Toolbar toolbar;
    @BindView(R.id.pic_tab)
    TabLayout picTab;
    @BindView(R.id.pic_page)
    ViewPager picPage;

    PicFraAdapter adapter;
    String id = "";
    String sensor = "";

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_pic);
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

        adapter = new PicFraAdapter(getSupportFragmentManager(), this, id, sensor);
        picPage.setAdapter(adapter);
        picTab.setupWithViewPager(picPage);
        picTab.setTabMode(TabLayout.MODE_FIXED);
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
