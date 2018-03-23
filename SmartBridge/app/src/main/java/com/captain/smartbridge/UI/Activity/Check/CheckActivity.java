package com.captain.smartbridge.UI.Activity.Check;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Activity.Monitor.fourgActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by captain on 18-3-23.
 */

public class CheckActivity extends AbsActivity{
    @BindView(R.id.check_toolbar)
    Toolbar toolbar;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_check);
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
