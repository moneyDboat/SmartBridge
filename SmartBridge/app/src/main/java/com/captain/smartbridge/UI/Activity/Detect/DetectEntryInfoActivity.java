package com.captain.smartbridge.UI.Activity.Detect;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ListView;

import com.captain.smartbridge.Common.CommonUtils;
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
 * Created by Captain on 17/6/28.
 */

public class DetectEntryInfoActivity extends AbsActivity {
    @BindView(R.id.detect_entry_info_toolbar)
    Toolbar toolbar;
    @BindView(R.id.detect_entry_infor_list)
    ListView deentryInforList;
    @BindView(R.id.detect_entry_button)
    Button deentryButton;

    DetectMission info = null;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_detectentry_info);
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

        deentryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(DetectEntryActivity.class);
            }
        });
    }

    private void setDetectInfo() {
        //从先前的Activity中获取检测任务数据
        String detectJson = getIntent().getStringExtra("detect");
        info = new Gson().fromJson(detectJson, DetectMission.class);

        List<SimpleText> texts = new ArrayList<>();
        texts.add(new SimpleText("任务代码", info.getJcrw_id()));
        texts.add(new SimpleText("桥梁代码", info.getQldm()));
        texts.add(new SimpleText("桥梁名称", info.getQlmc()));
        texts.add(new SimpleText("发布人员", info.getRwfbry()));
        texts.add(new SimpleText("发布时间", info.getRwfbsj()));
        texts.add(new SimpleText("状态", CommonUtils.getStatus(info.getStatus())));
        texts.add(new SimpleText("接收时间", info.getRejssj()));
        texts.add(new SimpleText("接收人员", info.getRwjsry()));
        texts.add(new SimpleText("完成时间", info.getRwwcsj()));
        String bz = info.getBz();
        if (bz.equals("None")){
            bz = "";
        }
        texts.add(new SimpleText("备注", bz));

        TextListAdapter adapter = new TextListAdapter(this, texts);
        deentryInforList.addHeaderView(new ViewStub(this));
        deentryInforList.setAdapter(adapter);
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
