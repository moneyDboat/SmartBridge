package com.captain.smartbridge.UI.Activity.Detect;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fish on 17-5-17.
 */

public class DetectEntryActivity extends AbsActivity implements View.OnClickListener{
    @BindView(R.id.detect_entry_toolbar)
    Toolbar toolbar;
    @BindView(R.id.entry_up_layout)
    RelativeLayout entryUpLayout;
    @BindView(R.id.entry_down_layout)
    RelativeLayout entryDownLayout;
    @BindView(R.id.entry_face_layout)
    RelativeLayout entryFaceLayout;
    @BindView(R.id.entry_spec_layout)
    RelativeLayout entrySpecLayout;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_detectentry);
    }

    @Override
    protected void prepareDatas() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        entryUpLayout.setOnClickListener(this);
        entryDownLayout.setOnClickListener(this);
        entryFaceLayout.setOnClickListener(this);
        entrySpecLayout.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

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
            case R.id.entry_up_layout:
                readyGo(EntryBuildActivity.class);
                break;
            case R.id.entry_down_layout:
                break;
            case R.id.entry_face_layout:
                break;
            case R.id.entry_spec_layout:
                break;
        }
    }
}
