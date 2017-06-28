package com.captain.smartbridge.UI.Activity.Detect;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.Common.PreferenceUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.model.CreateMissReq;
import com.captain.smartbridge.model.CreateMissRes;
import com.captain.smartbridge.model.Department;
import com.captain.smartbridge.model.MapReq;
import com.captain.smartbridge.model.MapRes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

public class DeCreateActivity extends AbsActivity {
    @BindView(R.id.decreate_toolbar)
    Toolbar toolbar;
    @BindView(R.id.decreate_code)
    Spinner decreateCode;
    @BindView(R.id.decreate_name)
    Spinner decreateName;
    @BindView(R.id.decreate_fbsj)
    TextView decreateFbsj;
    @BindView(R.id.decreate_jcdw)
    Spinner decreateJcdw;
    @BindView(R.id.decreate_fbry)
    TextView decreateFbry;
    @BindView(R.id.decreate_submit)
    Button decreateSubmit;

    List<Department> departments = null;
    List<MapRes> bridges = null;
    List<String> qldms = new ArrayList<>();
    List<String> qlmcs = new ArrayList<>();
    String jcdw = "0";
    String code = "";

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_decreate);
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

        initButton();


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        decreateFbsj.setText(simpleDateFormat.format(new Date()));
        decreateFbry.setText(PreferenceUtils.getString(this, PreferenceUtils.Key.USER));

    }

    //检测单位下拉列表
    private void initSpinner() {
        if (NetUtils.isNetworkAvailable(this)) {
            ApiManager.getmService().getDepart().enqueue(new Callback<List<Department>>() {
                @Override
                public void onResponse(Call<List<Department>> call, Response<List<Department>> response) {
                    departments = response.body();
                    List<String> items = new ArrayList<String>();
                    for (Department i : departments) {
                        items.add(i.getDepartName());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(DeCreateActivity.this, android.R.layout.simple_spinner_item, items);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    decreateJcdw.setAdapter(adapter);
                    decreateJcdw.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            jcdw = String.valueOf(position + 1);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    getBridge();
                }

                @Override
                public void onFailure(Call<List<Department>> call, Throwable t) {

                }
            });
        } else {
            showNetWorkError();
        }
    }

    //桥梁下拉列表
    private void getBridge() {
        String sf = PreferenceUtils.getString(this, PreferenceUtils.Key.SF);
        String cs = PreferenceUtils.getString(this, PreferenceUtils.Key.CF);
        if (sf.equals("")) {
            sf = "江苏省";
            cs = "南京市";
        }
        MapReq mapReq = new MapReq(sf, cs);
        ApiManager.getmService().getMapInfo(mapReq).enqueue(new Callback<List<MapRes>>() {
            @Override
            public void onResponse(Call<List<MapRes>> call, Response<List<MapRes>> response) {
                for (MapRes i : response.body()) {
                    qldms.add(i.getQldm());
                    qlmcs.add(i.getQlmc());
                }

                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(DeCreateActivity.this,
                        android.R.layout.simple_spinner_item, qldms);
                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(DeCreateActivity.this,
                        android.R.layout.simple_spinner_item, qlmcs);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                decreateCode.setAdapter(adapter1);
                decreateName.setAdapter(adapter2);
                decreateCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        decreateName.setSelection(position);
                        code = qldms.get(position);
                        showToast(code);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                decreateName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        decreateCode.setSelection(position);
                        code = qldms.get(position);
                        showToast(code);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<MapRes>> call, Throwable t) {

            }
        });
    }


    //提交按钮
    private void initButton() {
        decreateSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //后期添加提示对话框
                if (NetUtils.isNetworkAvailable(DeCreateActivity.this)) {
                    CreateMissReq createMissReq = new CreateMissReq(code, jcdw);

                    ApiManager.getmService().createDetect(createMissReq).enqueue(new Callback<CreateMissRes>() {
                        @Override
                        public void onResponse(Call<CreateMissRes> call, Response<CreateMissRes> response) {
                            showToast("检测任务创建成功！");

                            //延时关闭界面
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
                        public void onFailure(Call<CreateMissRes> call, Throwable t) {

                        }
                    });
                }
            }
        });
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
