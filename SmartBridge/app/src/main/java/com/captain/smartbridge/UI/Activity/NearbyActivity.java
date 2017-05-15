package com.captain.smartbridge.UI.Activity;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.ListView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Adapters.BridgeListAdapter;
import com.captain.smartbridge.model.BridgeList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fish on 17-5-15.
 */

public class NearbyActivity extends AbsActivity {
    @BindView(R.id.nearby_toolbar)
    Toolbar toolbar;
    @BindView(R.id.list_bridge)
    ListView listView;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_nearby);
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<BridgeList> bridgeLists = new ArrayList<>();
        bridgeLists.add(new BridgeList("南京第一长江大桥","G1001","江苏省南京市"));
        bridgeLists.add(new BridgeList("南京第二长江大桥","G1001","江苏省南京市"));
        BridgeListAdapter listAdapter = new BridgeListAdapter(this, bridgeLists);
        listView.addHeaderView(new ViewStub(this));
        listView.setAdapter(listAdapter);
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
