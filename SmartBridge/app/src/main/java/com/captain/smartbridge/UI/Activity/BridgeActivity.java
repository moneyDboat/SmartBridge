package com.captain.smartbridge.UI.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Adapters.FragementAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fish on 17-5-15.
 */

public class BridgeActivity extends AbsActivity {
    @BindView(R.id.bridge_toolbar)
    Toolbar toolbar;
    @BindView(R.id.bridge_page)
    ViewPager viewPager;
    @BindView(R.id.bridge_tab)
    TabLayout tabLayout;

    private FragementAdapter fragementAdapter;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_bridge);
    }

    @Override
    protected void prepareDatas() {
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragementAdapter = new FragementAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(fragementAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
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
