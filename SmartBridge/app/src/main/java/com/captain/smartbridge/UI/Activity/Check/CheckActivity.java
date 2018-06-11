package com.captain.smartbridge.UI.Activity.Check;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Activity.Check.Spec.List.SpecBriActivity;
import com.captain.smartbridge.UI.Activity.Detect.DeEntryActivity;
import com.captain.smartbridge.UI.Activity.Detect.DetectRecieveActivity;
import com.captain.smartbridge.UI.Activity.Detect.DetectStatusActivity;
import com.captain.smartbridge.UI.Activity.Detect.DetectSummaryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by captain on 18-3-23.
 */

public class CheckActivity extends AbsActivity implements View.OnClickListener{
    @BindView(R.id.check_toolbar)
    Toolbar toolbar;
    @BindView(R.id.pic_receive)
    LinearLayout recLayout;
    @BindView(R.id.pic_input)
    LinearLayout inputLayout;
    @BindView(R.id.pic_searmiss)
    LinearLayout searLayout;
    @BindView(R.id.pic_analye)
    LinearLayout analyeLayout;
    @BindView(R.id.pic_water)
    LinearLayout waterLayout;
    @BindView(R.id.pic_plane)
    LinearLayout planeLayout;

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

        recLayout.setOnClickListener(this);
        inputLayout.setOnClickListener(this);
        searLayout.setOnClickListener(this);
        analyeLayout.setOnClickListener(this);
        waterLayout.setOnClickListener(this);
        planeLayout.setOnClickListener(this);
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
        switch(v.getId()){
            case R.id.pic_receive:
                readyGo(DetectRecieveActivity.class);
                break;
                //readyGo(ReciveActivity.class);
            case R.id.pic_input:
                readyGo(DeEntryActivity.class);
                break;
                //readyGo(InputActivity.class);
            case R.id.pic_searmiss:
                //原来的检测状态
                readyGo(DetectStatusActivity.class);
                break;
                //readyGo(SearActivity.class);
            case R.id.pic_analye:
                //原来的检测汇总
                readyGo(DetectSummaryActivity.class);
                break;
                //readyGo(AnalyeActivity.class);
            case R.id.pic_water:
                Intent intent0 = new Intent(this, SpecBriActivity.class);
                intent0.putExtra("type", 0);
                startActivity(intent0);
                break;
            case R.id.pic_plane:
                Intent intent1 = new Intent(this, SpecBriActivity.class);
                intent1.putExtra("type", 1);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }
}
