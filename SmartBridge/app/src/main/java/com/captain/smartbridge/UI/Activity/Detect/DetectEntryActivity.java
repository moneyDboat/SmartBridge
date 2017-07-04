package com.captain.smartbridge.UI.Activity.Detect;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.model.FinishReq;
import com.captain.smartbridge.model.FinishRes;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by fish on 17-5-17.
 */

public class DetectEntryActivity extends AbsActivity implements View.OnClickListener{
    @BindView(R.id.detect_entry_toolbar)
    Toolbar toolbar;
    @BindView(R.id.entry_up_layout)
    RelativeLayout entryUpLayout;
    @BindView(R.id.entry_down_layout)
    RelativeLayout entryDownLayout;
    @BindView(R.id.entry_face_layout)
    RelativeLayout entryFaceLayout;
    @BindView(R.id.entry_spec_layout)
    RelativeLayout entrySpecLayout;
    @BindView(R.id.entry_submit)
    Button button;

    String rwid = null;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_detectentry);
    }

    @Override
    protected void prepareDatas() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rwid = getIntent().getStringExtra("id");

        entryUpLayout.setOnClickListener(this);
        entryDownLayout.setOnClickListener(this);
        entryFaceLayout.setOnClickListener(this);
        entrySpecLayout.setOnClickListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSubmitDialog();
            }
        });
    }

    private void showSubmitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否确定提交病害录入数据？");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                submit();
            }
        });
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void submit() {
        if(NetUtils.isNetworkConnected(this)){
            FinishReq req = new FinishReq(rwid);
            ApiManager.getmService().finish(req).enqueue(new Callback<FinishRes>() {
                @Override
                public void onResponse(Call<FinishRes> call, Response<FinishRes> response) {
                    showToast("病害数据上传完成！");

                    button.setClickable(false);
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
                public void onFailure(Call<FinishRes> call, Throwable t) {

                }
            });
        }else{
            showNetWorkError();
        }

    }

    @Override
    protected void initViews() {

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
    public void onClick(View view) {
        Intent intent = new Intent(this, EntryBuildActivity.class);
        intent.putExtra("Goujian", view.getId());
        startActivity(intent);
//        switch (view.getId()){
//            case R.id.entry_up_layout:
//                readyGo(EntryBuildActivity.class);
//                break;
//            case R.id.entry_down_layout:
//                break;
//            case R.id.entry_face_layout:
//                break;
//            case R.id.entry_spec_layout:
//                break;
//        }
    }
}
