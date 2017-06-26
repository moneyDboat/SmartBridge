package com.captain.smartbridge.UI.Activity.Detect;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.captain.smartbridge.Common.PreferenceUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fish on 17-5-14.
 */

public class DetectActivity extends AbsActivity implements View.OnClickListener{
    @BindView(R.id.detect_toolbar)
    Toolbar toolbar;
    @BindView(R.id.asign_layout)
    LinearLayout asignLayout;
    @BindView(R.id.detect_layout)
    LinearLayout detectLayout;
    @BindView(R.id.detect_status)
    RelativeLayout statusLayout;
    @BindView(R.id.detect_summary)
    RelativeLayout detectSummary;
    @BindView(R.id.detect_recieve)
    RelativeLayout recieveLayout;
    @BindView(R.id.detect_entry)
    RelativeLayout entryLayout;
    @BindView(R.id.asign_summary)
    RelativeLayout asignSummary;


    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_detect);
    }

    @Override
    protected void prepareDatas() {
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        statusLayout.setOnClickListener(this);
        asignSummary.setOnClickListener(this);
        detectSummary.setOnClickListener(this);
        recieveLayout.setOnClickListener(this);
        entryLayout.setOnClickListener(this);



        //init view by user category
        //具体还需要修改
        switch (PreferenceUtils.getInt(this, PreferenceUtils.Key.ROLE)){
            case(1):
                detectLayout.setVisibility(View.GONE);
                break;
            case(2):
                detectLayout.setVisibility(View.GONE);
                break;
            case(3):
                asignLayout.setVisibility(View.GONE);
                break;
            default:
                break;
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
    public void onClick(View view) {
        switch (view.getId()){
            //检测状态
            case (R.id.detect_status):
                readyGo(DetectStatusActivity.class);
                break;

            //检测汇总
            case (R.id.asign_summary):
                readyGo(DetectSummaryActivity.class);
                break;

            //检测接收
            case (R.id.detect_recieve):
                readyGo(DetectRecieveActivity.class);
                break;

            //检测录入
            case (R.id.detect_entry):
                readyGo(DetectEntryActivity.class);
                break;

            //检测汇总
            case (R.id.detect_summary):
                readyGo(DetectSummaryActivity.class);
                break;

            default:
                break;
        }
    }
}
