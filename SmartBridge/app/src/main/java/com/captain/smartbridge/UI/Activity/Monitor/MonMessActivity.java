package com.captain.smartbridge.UI.Activity.Monitor;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.other.MonMesFraAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Captain on 17/7/4.
 */

public class MonMessActivity extends AbsActivity {
    @BindView(R.id.momession_toolbar)
    Toolbar toolbar;
    @BindView(R.id.monmession_tab)
    TabLayout monmessionTab;
    @BindView(R.id.monmession_page)
    ViewPager monmessionPage;


    MonMesFraAdapter fraAdapter;


    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_momession);
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fraAdapter = new MonMesFraAdapter(getSupportFragmentManager(), this);
        monmessionPage.setAdapter(fraAdapter);
        monmessionTab.setupWithViewPager(monmessionPage);
        monmessionTab.setTabMode(TabLayout.MODE_FIXED);
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
