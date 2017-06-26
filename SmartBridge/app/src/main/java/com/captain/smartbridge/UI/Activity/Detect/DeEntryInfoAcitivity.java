package com.captain.smartbridge.UI.Activity.Detect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fish on 17-6-26.
 */

public class DeEntryInfoAcitivity extends AbsActivity {
    @BindView(R.id.deentry_info_toolbar)
    Toolbar toolbar;
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
                if(data!=null){
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
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
