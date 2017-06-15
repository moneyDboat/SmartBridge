package com.captain.smartbridge.UI.Activity.Detect;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fish on 17-5-17.
 */

public class DeCreateActivity extends AbsActivity {
    @BindView(R.id.decreate_toolbar)
    Toolbar toolbar;
    @OnClick(R.id.decreate_submit)
    public void submit(){
        //新建任务提交，弹出对话框
        finish();
    }

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_decreate);
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

    //获取检测单位
    private void getDep(){

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
