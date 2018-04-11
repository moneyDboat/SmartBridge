package com.captain.smartbridge.UI.Activity.Check.Regular;

import android.view.MenuItem;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;

import butterknife.ButterKnife;

/**
 * Created by Captain on 18/4/11.
 */

public class ReciveActivity extends AbsActivity {
    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_recive);
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
