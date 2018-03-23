package com.captain.smartbridge.UI.Activity.Monitor;

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
 * Created by captain on 18-3-23.
 */

public class fourgActivity extends AbsActivity {
    @BindView(R.id.fourg_toolbar)
    Toolbar toolbar;
    @BindView(R.id.fourg_tab)
    TabLayout fourgTab;
    @BindView(R.id.fourg_page)
    ViewPager fourgPage;

    FourFraAdapter fourFraAdapter;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_fourg);
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fourFraAdapter = new FourFraAdapter(getSupportFragmentManager(), this);
        fourgPage.setAdapter(fourFraAdapter);
        fourgTab.setupWithViewPager(fourgPage);
        fourgTab.setTabMode(TabLayout.MODE_FIXED);
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
