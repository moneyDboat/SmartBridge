package com.captain.smartbridge.UI.Activity.Monitor;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Activity.BaseApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by captain on 18-3-23.
 */

public class MonitorActivity extends AbsActivity {
    @BindView(R.id.monitor_toolbar)
    Toolbar toolbar;
    @BindView(R.id.pic_things)
    LinearLayout picThings;
    @BindView(R.id.pic_4g)
    LinearLayout pic4g;
    @BindView(R.id.pic_pic)
    LinearLayout picPic;
    @BindView(R.id.pic_top)
    LinearLayout picTop;
    @BindView(R.id.text_speed)
    TextView textSpeed;
    @BindView(R.id.text_support)
    TextView textSupport;
    @BindView(R.id.text_flex)
    TextView textFlex;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_monitor);
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pic4g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(fourgActivity.class);
            }
        });
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
