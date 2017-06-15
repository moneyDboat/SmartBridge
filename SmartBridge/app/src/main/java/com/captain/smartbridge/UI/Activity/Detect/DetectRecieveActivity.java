package com.captain.smartbridge.UI.Activity.Detect;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.DeMissionListAdapter;
import com.captain.smartbridge.model.Mission;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fish on 17-5-17.
 */

public class DetectRecieveActivity extends AbsActivity {
    @BindView(R.id.dereceive_toolbar)
    Toolbar toolbar;
    @BindView(R.id.dereceive_list)
    ListView listView;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_derecieve);
    }

    @Override
    protected void prepareDatas() {
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<Mission> missions = new ArrayList<>();
        missions.add(new Mission("南京长江大桥","G1001","监管单位","检测单位",0));
        missions.add(new Mission("南京第二大桥","G1001","监管单位","检测单位",0));
        missions.add(new Mission("南京第三大桥","G1001","监管单位","检测单位",0));
        missions.add(new Mission("南京第四大桥","G1001","监管单位","检测单位",0));
        missions.add(new Mission("南京第五大桥","G1001","监管单位","检测单位",0));
        DeMissionListAdapter adapter = new DeMissionListAdapter(this, missions);
        listView.setHeaderDividersEnabled(true);
        listView.setAdapter(adapter);

    }

    private void getMission(){

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
