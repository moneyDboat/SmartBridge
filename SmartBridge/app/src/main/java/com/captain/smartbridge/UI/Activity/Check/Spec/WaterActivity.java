package com.captain.smartbridge.UI.Activity.Check.Spec;

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
 * Created by captain on 18-4-11.
 */

public class WaterActivity extends AbsActivity {
    @BindView(R.id.water_toolbar)
    Toolbar toolbar;
    @BindView(R.id.water_page)
    ViewPager waterPage;
    @BindView(R.id.water_tab)
    TabLayout waterTab;

    PicFraAdapter adapter;
    String id = "";
    String sensor = "";

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_water);
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //获取桥梁传感器
//        id = getIntent().getStringExtra("id");
//        sensor = getIntent().getStringExtra("sensor");

        adapter = new PicFraAdapter(getSupportFragmentManager(), this, id, sensor);
        waterPage.setAdapter(adapter);
        waterTab.setupWithViewPager(waterPage);
        waterTab.setTabMode(TabLayout.MODE_FIXED);
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
