package com.captain.smartbridge.UI.Activity.Detect;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.captain.smartbridge.Common.PreferenceUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.BuildEntryAdapter;
import com.captain.smartbridge.model.BinghaiRes;
import com.captain.smartbridge.model.BuildEntryRes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fish on 17-6-13.
 */

public class EntryBuildActivity extends AbsActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.entry_build_toolbar)
    Toolbar toolbar;
    @BindView(R.id.entry_build_list)
    ListView entryBuildList;

    private List<BuildEntryRes> builds;
    private List<BinghaiRes> bings;
    private List<String> strings = new ArrayList<>();

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
        int id = getIntent().getIntExtra("Goujian", 0);
        String key = "";
        switch (id) {
            case (0):
                break;
            case (R.id.entry_up_layout):
                key = PreferenceUtils.Key.UPGOU;
                setTitle("上部构件病害录入");
                break;
            case (R.id.entry_down_layout):
                key = PreferenceUtils.Key.DOWNGOU;
                setTitle("下部构件病害录入");
                break;
            case (R.id.entry_face_layout):
                key = PreferenceUtils.Key.QIAOMIAN;
                setTitle("桥面系构件病害录入");
                break;
            case (R.id.entry_spec_layout):
                key = PreferenceUtils.Key.DANDU;
                setTitle("特殊构件病害录入");
                break;
        }

        //获取构件信息
        initList(key);

        entryBuildList.setOnItemClickListener(this);
    }

    private void initList(String key) {
        //        List<BuildRes> builds = new ArrayList<>();
        //        builds.add(new BuildRes("",4,"","","顶板"));
        //        builds.add(new BuildRes("",4,"","","底板"));
        //        builds.add(new BuildRes("",4,"","","支座"));
        //        builds.add(new BuildRes("",4,"","","主梁"));
        //        builds.add(new BuildRes("",4,"","","横隔板"));
        //        builds.add(new BuildRes("",4,"","","湿接缝"));
        //        BuildEntryAdapter adapter = new BuildEntryAdapter(this, builds);
        //        entryBuildList.setAdapter(adapter);
        String buildsJson = PreferenceUtils.getString(this, key);
        Type type = new TypeToken<List<BuildEntryRes>>() {
        }.getType();
        builds = new Gson().fromJson(buildsJson, type);

        String bingJson = PreferenceUtils.getString(this, PreferenceUtils.Key.BINGHAI);
        Type type1 = new TypeToken<List<BinghaiRes>>() {
        }.getType();
        bings = new Gson().fromJson(bingJson, type1);

        for (BuildEntryRes build : builds) {
            strings.add(build.getGjmc());
        }

        BuildEntryAdapter adapter = new BuildEntryAdapter(this, strings);
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
        BuildEntryRes build = builds.get(position);
        Intent intent = new Intent(EntryBuildActivity.this, DeEntryInfoAcitivity.class);
        intent.putExtra("Goujian", new Gson().toJson(build));

        //使用构件代码遍历获取构件对应的病害类型
        //如果没有则默认使用主梁100131的病害类型
        BinghaiRes bing = null;
        BinghaiRes bingDefault = null;
        for (BinghaiRes i : bings) {
            if (i.getGjdm().equals("100131")) {
                bingDefault = i;
            }
            if (i.getGjdm().equals(build.getGjdm())) {
                bing = i;
                break;
            }
        }
        if (bing == null) {
            bing = bingDefault;
        }
        intent.putExtra("Binghai", new Gson().toJson(bing));

        startActivity(intent);
        //        readyGo(DeEntryInfoAcitivity.class);
    }
}
