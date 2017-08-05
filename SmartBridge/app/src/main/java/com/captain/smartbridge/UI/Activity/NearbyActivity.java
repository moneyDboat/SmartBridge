package com.captain.smartbridge.UI.Activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ListView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Adapters.BridgeListAdapter;
import com.captain.smartbridge.model.MapRes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fish on 17-5-15.
 */

public class NearbyActivity extends AbsActivity implements AdapterView.OnItemClickListener{
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

        initList();
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

    private void initList(){
        //由原来的从服务器获取改为从MainActivity获取
        //解决可能出现的次序问题
        String bridgesJson = getIntent().getStringExtra("bridges");
        Type listType = new TypeToken<List<MapRes>>(){}.getType();
        List<MapRes> bridges = new Gson().fromJson(bridgesJson, listType);
        BridgeListAdapter listAdapter = new BridgeListAdapter(this, bridges);
        listView.addHeaderView(new ViewStub(this));
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        intent.putExtra("ID", i);
        NearbyActivity.this.setResult(1, intent);
        NearbyActivity.this.finish();
    }
}
