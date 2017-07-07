package com.captain.smartbridge.UI.Activity.Monitor;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Captain on 17/7/7.
 */

public class SensorCurveActivity extends AbsActivity{
    @BindView(R.id.sensor_toolbar)
    Toolbar toolbar;
    @BindView(R.id.sensor_list)
    ListView sensorList;
    @BindView(R.id.sensor_swipe)
    SwipeRefreshLayout sensorSwipe;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_sensor);
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
