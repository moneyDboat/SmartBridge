package com.captain.smartbridge.UI.Activity.Monitor;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.View.CircularRingPercentageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Captain on 17/12/9.
 */

public class EleRingActivity extends AbsActivity {
    @BindView(R.id.ele_ring)
    CircularRingPercentageView eleRing;
    @BindView(R.id.elering_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.elering_ele_text)
    TextView eleText;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_elering);
    }

    @Override
    protected void prepareDatas() {
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //设置电量圆环
        eleRing.setLine(false);
        Float ele = getIntent().getFloatExtra("ele", 0);
        eleText.setText(String.valueOf(ele)+'V');
        int percent = calPercent(ele);
        eleRing.setProgress(percent);
    }

    //计算电量百分比
    private int calPercent(Float ele) {
        if (ele >= 4.2) {
            return 100;
        } else if (ele >= 4.06) {
            return 90;
        } else if (ele >= 3.98) {
            return 80;
        } else if (ele >= 3.92) {
            return 70;
        } else if (ele >= 3.87) {
            return 60;
        } else if (ele >= 3.82) {
            return 50;
        } else if (ele >= 3.79) {
            return 40;
        } else if (ele >= 3.77) {
            return 30;
        } else if (ele >= 3.74) {
            return 20;
        } else if (ele >= 3.68) {
            return 10;
        } else if (ele >= 3.45) {
            return 5;
        } else {
            return 0;
        }
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
