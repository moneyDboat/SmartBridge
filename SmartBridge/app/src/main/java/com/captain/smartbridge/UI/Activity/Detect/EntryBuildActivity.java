package com.captain.smartbridge.UI.Activity.Detect;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.BuildEntryAdapter;
import com.captain.smartbridge.model.BuildRes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fish on 17-6-13.
 */

public class EntryBuildActivity extends AbsActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.entry_build_toolbar)
    Toolbar toolbar;
    @BindView(R.id.entry_build_list)
    ListView entryBuildList;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_entry_build);
    }

    @Override
    protected void prepareDatas() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initViews() {
        initList();
    }

    private void initList() {
        List<BuildRes> builds = new ArrayList<>();
        builds.add(new BuildRes("",4,"","","顶板"));
        builds.add(new BuildRes("",4,"","","底板"));
        builds.add(new BuildRes("",4,"","","支座"));
        builds.add(new BuildRes("",4,"","","主梁"));
        builds.add(new BuildRes("",4,"","","横隔板"));
        builds.add(new BuildRes("",4,"","","湿接缝"));
        BuildEntryAdapter adapter = new BuildEntryAdapter(this, builds);
        entryBuildList.setAdapter(adapter);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        readyGo(DeEntryInfoAcitivity.class);
    }
}
