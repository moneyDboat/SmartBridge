package com.captain.smartbridge.UI.Activity.Detect;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ListView;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.TextListAdapter;
import com.captain.smartbridge.model.AcceptMissReq;
import com.captain.smartbridge.model.AcceptMissRes;
import com.captain.smartbridge.model.DetectMission;
import com.captain.smartbridge.model.SimpleText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Captain on 17/6/27.
 */

public class DetectRecieveInfoActivity extends AbsActivity {
    @BindView(R.id.dereceive_info_toolbar)
    Toolbar toolbar;
    @BindView(R.id.dereceive_infor_list)
    ListView dereceiveInforList;
    @BindView(R.id.derecieve_button)
    Button derecieveButton;

    DetectMission info = null;
    AlertDialog.Builder builder;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_derecieve_info);
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

        derecieveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
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
        texts.add(new SimpleText("状态", info.getStatus()));
        texts.add(new SimpleText("接收时间", info.getRejssj()));
        texts.add(new SimpleText("接收人员", info.getRwjsry()));
        texts.add(new SimpleText("完成时间", info.getRwwcsj()));
        texts.add(new SimpleText("备注", info.getBz()));

        TextListAdapter adapter = new TextListAdapter(this, texts);
        dereceiveInforList.addHeaderView(new ViewStub(this));
        dereceiveInforList.setAdapter(adapter);
    }

    private void recieveMission(){
        if (NetUtils.isNetworkAvailable(this)){
            AcceptMissReq acceptMissReq = new AcceptMissReq(info.getJcrw_id());
            ApiManager.getmService().acceptDetect(acceptMissReq).enqueue(new Callback<AcceptMissRes>() {
                @Override
                public void onResponse(Call<AcceptMissRes> call, Response<AcceptMissRes> response) {
                    showToast("接收任务成功");

                    Timer timer = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            finish();
                        }
                    };
                    timer.schedule(timerTask, 2000);
                }

                @Override
                public void onFailure(Call<AcceptMissRes> call, Throwable t) {

                }
            });
        }else{
            showNetWorkError();
        }
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


   //显示对话框
    private void showDialog(){
        builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否确定接收该检测任务？");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recieveMission();
            }
        });
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
