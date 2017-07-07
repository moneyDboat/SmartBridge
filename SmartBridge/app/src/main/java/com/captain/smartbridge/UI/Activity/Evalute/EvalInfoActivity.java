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
import com.captain.smartbridge.UI.Adapters.TextListAdapter;
import com.captain.smartbridge.model.SimpleText;
import com.captain.smartbridge.model.other.Evalution;
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
                //intent添加数据
                startActivity(intent);
            }
        });
    }

    private void setEvalInfo() {
        //从前一个activity获取数据
        String evalJson = getIntent().getStringExtra("eval");
        Evalution evalution = new Gson().fromJson(evalJson, Evalution.class);

        List<SimpleText> texts = new ArrayList<>();
        texts.add(new SimpleText("桥梁名称", evalution.getName()));
        texts.add(new SimpleText("桥梁结构类型", evalution.getType()));
        texts.add(new SimpleText("桥梁位置", evalution.getLocation()));
        texts.add(new SimpleText("管养单位", evalution.getManage()));
        texts.add(new SimpleText("检测人", evalution.getDetector()));
        texts.add(new SimpleText("检测单位", evalution.getDetectDepart()));
        texts.add(new SimpleText("检测时间", evalution.getDetectDate()));
        texts.add(new SimpleText("评分", evalution.getGrade()));
        texts.add(new SimpleText("等级", evalution.getRate()));

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
