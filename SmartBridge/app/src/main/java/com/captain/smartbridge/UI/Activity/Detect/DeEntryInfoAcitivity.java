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
import android.widget.Spinner;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fish on 17-6-26.
 */

public class DeEntryInfoAcitivity extends AbsActivity {
    @BindView(R.id.deentry_info_toolbar)
    Toolbar toolbar;
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

    private List<String> items1 = new ArrayList<>();
    private ArrayAdapter<String> adapter1 = null;

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

        initSpinner();


        //调用相机
        deentryPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        });
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
        List<String> items0 = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            items0.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter0 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, items0);
        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        detectInfoSpinner0.setAdapter(adapter0);
        detectInfoSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = String.valueOf(position + 1);
                items1.add("顶板_" + s + "_1");
                items1.add("顶板_" + s + "_2");
                items1.add("顶板_" + s + "_3");
                items1.add("顶板_" + s + "_4");
                adapter1 = new ArrayAdapter<>(DeEntryInfoAcitivity.this,
                        android.R.layout.simple_spinner_item, items1);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                detectInfoSpinner1.setAdapter(adapter1);
                detectInfoSpinner1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
