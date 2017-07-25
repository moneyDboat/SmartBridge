package com.captain.smartbridge.UI.Activity.Evalute;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ListView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Activity.BaseApplication;
import com.captain.smartbridge.UI.Adapters.TextListAdapter;
import com.captain.smartbridge.model.SimpleText;
import com.captain.smartbridge.model.other.EvaluteMess;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Captain on 17/7/8.
 */

public class EvalInfoActivity extends AbsActivity {
    @BindView(R.id.evalinfo_toolbar)
    Toolbar toolbar;
    @BindView(R.id.evalinfo_list)
    ListView evalinfoList;
    @BindView(R.id.evalinfo_button)
    Button button;

    EvaluteMess evaluteMess = null;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_evalinfo);
    }

    @Override
    protected void prepareDatas() {
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setEvalInfo();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EvalInfoActivity.this, EvalActivity.class);
                BaseApplication.setEVAID(evaluteMess.getQldm());
                startActivity(intent);
            }
        });
    }

    private void setEvalInfo() {
        //从前一个activity获取数据
        String evalJson = getIntent().getStringExtra("eval");
        evaluteMess = new Gson().fromJson(evalJson, EvaluteMess.class);

        List<SimpleText> texts = new ArrayList<>();
        texts.add(new SimpleText("桥梁名称", evaluteMess.getQlmc()));
        texts.add(new SimpleText("桥梁代码", evaluteMess.getQldm()));
        texts.add(new SimpleText("桥梁结构类型", evaluteMess.getQllx()));
        texts.add(new SimpleText("桥梁位置", evaluteMess.getQlwz()));
        texts.add(new SimpleText("管养单位", " "));
        texts.add(new SimpleText("检测人", evaluteMess.getRwjsry()));
        texts.add(new SimpleText("检测单位", evaluteMess.getDepartName()));
        texts.add(new SimpleText("检测时间", evaluteMess.getRwwcsj()));
        texts.add(new SimpleText("评分", evaluteMess.getScore().substring(6)));
        texts.add(new SimpleText("等级", String.valueOf(evaluteMess.getLevel())));

        TextListAdapter adapter = new TextListAdapter(this, texts);
        evalinfoList.addHeaderView(new ViewStub(this));
        evalinfoList.setAdapter(adapter);
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
