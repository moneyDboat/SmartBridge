package com.captain.smartbridge.UI.Activity.Check.Regular;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by captain on 18-4-11.
 */

public class InputActivity extends AbsActivity{
    @BindView(R.id.input_toolbar)
    Toolbar toolbar;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_input);
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
