package com.captain.smartbridge.UI.Activity.Detect;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.model.BinghaiRes;
import com.captain.smartbridge.model.BuildEntryRes;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fish on 17-6-26.
 */

public class DeEntryInfoAcitivity extends AbsActivity {
    @BindView(R.id.deentry_info_toolbar)
    Toolbar toolbar;
    @BindView(R.id.detect_info_spinLayout)
    LinearLayout spinLayout;
    @BindView(R.id.detect_info_spinner0)
    Spinner detectInfoSpinner0;
    @BindView(R.id.detect_info_spinner1)
    Spinner detectInfoSpinner1;
    @BindView(R.id.detect_info_spinner2)
    Spinner detectInfoSpinner2;
    @BindView(R.id.detect_info_spinner3)
    Spinner detectInfoSpinner3;
    @BindView(R.id.deentry_des)
    EditText deentryDes;
    @BindView(R.id.deentry_pic)
    ImageView deentryPic;
    @BindView(R.id.deentry_submit)
    Button deentrySubmit;

    private List<String> items0 = new ArrayList<>();
    private List<String> items1 = new ArrayList<>();
    private List<String> items2 = new ArrayList<>();
    private List<String> items3 = new ArrayList<>();
    private ArrayAdapter<String> adapter1 = null;
    private BuildEntryRes build;
    private BinghaiRes bing;
    private List<BuildEntryRes.JtgjBean> jtgjs;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_deentry_info);
    }

    @Override
    protected void prepareDatas() {
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //获取具体构件类型和相应的病害类型
        String buildJson = getIntent().getStringExtra("Goujian");
        build = new Gson().fromJson(buildJson, BuildEntryRes.class);
        String bingJson = getIntent().getStringExtra("Binghai");
        bing = new Gson().fromJson(bingJson, BinghaiRes.class);

        initSpinner();

        deentrySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSubmitDialog();
            }
        });


        //调用相机
        deentryPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void showSubmitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否确定保存当前构件的病害信息？");
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
        showToast("当前构件病害信息已保存");

        deentrySubmit.setClickable(false);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        };
        timer.schedule(timerTask, 2000);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    Bitmap bmp = (Bitmap) extras.get("data");

                    deentryPic.setImageBitmap(bmp);  //设置照片现实在界面上
                }
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showDialog();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //显示对话框
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("当前构件病害录入还未完成，是否退出？");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //设置所有的下拉列表
    private void initSpinner() {
        //垮数下拉列表
        if (build.getKs_sum() == -2) {
            spinLayout.setVisibility(View.GONE);
        } else {
            jtgjs = build.getJtgj();
            for (BuildEntryRes.JtgjBean jtgj : jtgjs) {
                items0.add(jtgj.getKs());
            }
            ArrayAdapter<String> adapter0 = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, items0);
            adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            detectInfoSpinner0.setAdapter(adapter0);
            detectInfoSpinner0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //具体构件下拉列表
                    items1 = new ArrayList<String>();
                    for (BuildEntryRes.JtgjBean.JtgjsBean jtgj : jtgjs.get(position).getJtgjs()) {
                        items1.add(jtgj.getGjjtmc());
                        adapter1 = new ArrayAdapter<>(DeEntryInfoAcitivity.this,
                                android.R.layout.simple_spinner_item, items1);
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        detectInfoSpinner1.setAdapter(adapter1);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        //病害类型和病害标度的下拉列表
        //没有的按照主梁类型病害来
        //主梁：100031
        for (BinghaiRes.BhlxBean b : bing.getBhlx()) {
            items2.add(b.getBhlxmc());
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        detectInfoSpinner2.setAdapter(adapter2);
        detectInfoSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                items3 = new ArrayList<String>();
                int max = bing.getBhlx().get(position).getMaxbhbd();
                for (int i = 0; i < max; i++) {
                    items3.add(String.valueOf(i));
                }
                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(DeEntryInfoAcitivity.this,
                        android.R.layout.simple_spinner_item, items3);
                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                detectInfoSpinner3.setAdapter(adapter3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
