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
                break;
            case R.id.pic_input:
                break;
            case R.id.pic_searmiss:
                break;
            case R.id.pic_analye:
                break;
            case R.id.pic_water:
                break;
            case R.id.pic_plane:
                break;
            default:
                break;
        }
    }
}
