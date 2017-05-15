package com.captain.smartbridge.UI.Activity;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.captain.smartbridge.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fish on 17-5-15.
 */

public class BridgeActivity extends AbsActivity {
    @BindView(R.id.bridge_toolbar)
    Toolbar toolbar;

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
