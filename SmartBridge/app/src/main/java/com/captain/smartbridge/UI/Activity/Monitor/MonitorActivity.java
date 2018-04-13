package com.captain.smartbridge.UI.Activity.Monitor;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Activity.Monitor.Noise.FlexActivity;
import com.captain.smartbridge.UI.Activity.Monitor.Noise.SpeedActivity;
import com.captain.smartbridge.UI.Activity.Monitor.Noise.SupportActivity;
import com.captain.smartbridge.UI.Activity.Monitor.Wireless.FourGActivity;
import com.captain.smartbridge.UI.Activity.Monitor.Wireless.PicActivity;
import com.captain.smartbridge.UI.Activity.Monitor.Wireless.ThingsActivity;
import com.captain.smartbridge.UI.Activity.Monitor.Wireless.TopActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by captain on 18-3-23.
 */

public class MonitorActivity extends AbsActivity implements View.OnClickListener {
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

        picThings.setOnClickListener(this);
        pic4g.setOnClickListener(this);
        picPic.setOnClickListener(this);
        picTop.setOnClickListener(this);
        textSpeed.setOnClickListener(this);
        textSupport.setOnClickListener(this);
        textFlex.setOnClickListener(this);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.pic_things):
                readyGo(ThingsActivity.class);
                break;
            case (R.id.pic_4g):
                readyGo(FourGActivity.class);
                break;
            case (R.id.pic_pic):
                readyGo(PicActivity.class);
                break;
            case (R.id.pic_top):
                readyGo(TopActivity.class);
                break;
            case (R.id.text_speed):
                readyGo(SpeedActivity.class);
                break;
            case (R.id.text_support):
                readyGo(SupportActivity.class);
                break;
            case (R.id.text_flex):
                readyGo(FlexActivity.class);
                break;
            default:
                break;
        }
    }
}
