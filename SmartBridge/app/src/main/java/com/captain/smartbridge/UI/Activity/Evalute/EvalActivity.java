package com.captain.smartbridge.UI.Activity.Evalute;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Captain on 17/7/8.
 */

public class EvalActivity extends AbsActivity {
    @BindView(R.id.evalution_toolbar)
    Toolbar toolbar;
    @BindView(R.id.evalution_page)
    ViewPager viewPager;
    @BindView(R.id.evalution_tab)
    TabLayout tabLayout;

    private EvalFragAdapter fragementAdapter;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_evalution);
    }

    @Override
    protected void prepareDatas() {
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getIntent().getStringExtra("ID");

        fragementAdapter = new EvalFragAdapter(getSupportFragmentManager(), this);
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
