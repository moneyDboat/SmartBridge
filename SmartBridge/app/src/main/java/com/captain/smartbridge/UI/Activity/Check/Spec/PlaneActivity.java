package com.captain.smartbridge.UI.Activity.Check.Spec;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.other.FourFraAdapter;
import com.captain.smartbridge.UI.Adapters.other.PlaneFraAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by captain on 18-4-11.
 */

public class PlaneActivity extends AbsActivity {
    @BindView(R.id.plane_toolbar)
    Toolbar toolbar;
    @BindView(R.id.plane_tab)
    TabLayout planeTab;
    @BindView(R.id.plane_page)
    ViewPager planePage;

    PlaneFraAdapter planeFraAdapter;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_plane);
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        planeFraAdapter = new PlaneFraAdapter(getSupportFragmentManager(), this);
        planePage.setAdapter(planeFraAdapter);
        planeTab.setupWithViewPager(planePage);
        planeTab.setTabMode(TabLayout.MODE_FIXED);
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
