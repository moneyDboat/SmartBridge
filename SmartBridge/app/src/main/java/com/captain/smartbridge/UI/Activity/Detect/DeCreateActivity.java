package com.captain.smartbridge.UI.Activity.Detect;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.Common.PreferenceUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.model.Department;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    EditText decreateCode;
    @BindView(R.id.decreate_name)
    EditText decreateName;
    @BindView(R.id.decreate_fbsj)
    TextView decreateFbsj;
    @BindView(R.id.decreate_jcdw)
    Spinner decreateJcdw;
    @BindView(R.id.decreate_fbry)
    TextView decreateFbry;
    @BindView(R.id.decreate_submit)
    Button decreateSubmit;

    @OnClick(R.id.decreate_submit)
    public void submit() {
        //新建任务提交，弹出对话框
        finish();
    }

    List<Department> departments = null;
    String jcdw = "0";

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


        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        decreateFbsj.setText(simpleDateFormat.format(new Date()));
        decreateFbry.setText(PreferenceUtils.getString(this,PreferenceUtils.Key.USER));

    }

    //检测单位下拉列表
    private void initSpinner() {
        if (NetUtils.isNetworkAvailable(this)){
            ApiManager.getmService().getDepart().enqueue(new Callback<List<Department>>() {
                @Override
                public void onResponse(Call<List<Department>> call, Response<List<Department>> response) {
                    departments = response.body();
                    List<String> items = new ArrayList<String>();
                    for(Department i:departments){
                        items.add(i.getDepartName());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(DeCreateActivity.this, android.R.layout.simple_spinner_item, items);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    decreateJcdw.setAdapter(adapter);
                    decreateJcdw.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            jcdw = String.valueOf(position+1);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                @Override
                public void onFailure(Call<List<Department>> call, Throwable t) {

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
}
