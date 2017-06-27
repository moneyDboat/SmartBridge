package com.captain.smartbridge.UI.Activity.Detect;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.ListView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.TextListAdapter;
import com.captain.smartbridge.model.DetectMission;
import com.captain.smartbridge.model.SimpleText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fish on 17-6-7.
 */

public class DetectInfoActivity extends AbsActivity {
    @BindView(R.id.detect_info_toolbar)
    Toolbar toolbar;
    @BindView(R.id.detect_infor_listview)
    ListView detectInforListview;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_detect_info);
    }

    @Override
    protected void prepareDatas() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setDetectInfo();
    }

    private void setDetectInfo(){
        //从先前的Activity中获取检测任务数据
        String detectJson = getIntent().getStringExtra("detect");
        DetectMission info = new Gson().fromJson(detectJson, DetectMission.class);

        List<SimpleText> texts = new ArrayList<>();
        texts.add(new SimpleText("任务代码", info.getJcrw_id()));
        texts.add(new SimpleText("桥梁代码", info.getQldm()));
        texts.add(new SimpleText("桥梁名称", info.getQlmc()));
        texts.add(new SimpleText("发布人员", info.getRwfbry()));
        texts.add(new SimpleText("发布时间", info.getRwfbsj()));
        texts.add(new SimpleText("状态", info.getStatus()));
        texts.add(new SimpleText("接受时间", info.getRejssj()));
        texts.add(new SimpleText("接受人员", info.getRwjsry()));
        texts.add(new SimpleText("完成时间", info.getRwwcsj()));
        texts.add(new SimpleText("备注", info.getBz()));

        TextListAdapter adapter = new TextListAdapter(this, texts);
        detectInforListview.addHeaderView(new ViewStub(this));
        detectInforListview.setAdapter(adapter);
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
