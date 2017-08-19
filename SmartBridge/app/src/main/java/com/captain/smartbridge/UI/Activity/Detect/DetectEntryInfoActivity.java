package com.captain.smartbridge.UI.Activity.Detect;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ListView;

import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.CommonUtils;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.Common.PreferenceUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.AbsActivity;
import com.captain.smartbridge.UI.Adapters.TextListAdapter;
import com.captain.smartbridge.model.BinghaiRes;
import com.captain.smartbridge.model.BuildEntryRes;
import com.captain.smartbridge.model.DetectMission;
import com.captain.smartbridge.model.SearchCodeReq;
import com.captain.smartbridge.model.SimpleText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.detect_entry_download)
    Button downloadButton;


    DetectMission info = null;
    boolean ifDownload = false;
    String bridgeCode = null;
    String rwid = null;

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
                if (ifDownload) {
                    Intent intent = new Intent(DetectEntryInfoActivity.this, DetectEntryActivity.class);
                    intent.putExtra("id", rwid);
                    startActivity(intent);
                } else {
                    showToast("请先下载数据");
                }
            }
        });

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBuild();
            }
        });
    }

    private void getBuild() {
        if (bridgeCode == null) {
            return;
        }

        SearchCodeReq req = new SearchCodeReq(bridgeCode);
        if (NetUtils.isNetworkAvailable(DetectEntryInfoActivity.this)) {
            getUp(req);
            showToast("数据正在下载中");
        } else {
            showNetWorkError();
        }
    }

    private void getUp(final SearchCodeReq req) {
        ApiManager.getmService().upGouJian(req).enqueue(new Callback<List<BuildEntryRes>>() {
            @Override
            public void onResponse(Call<List<BuildEntryRes>> call, Response<List<BuildEntryRes>> response) {
                if (response.body() == null) {
                    showToast("账户登录过期，请退出账户后重新登录");
                    return;
                }

                PreferenceUtils.putString(DetectEntryInfoActivity.this, PreferenceUtils.Key.UPGOU,
                        new Gson().toJson(response.body()));
                getDown(req);
            }

            @Override
            public void onFailure(Call<List<BuildEntryRes>> call, Throwable t) {

            }
        });
    }

    private void getDown(final SearchCodeReq req) {
        ApiManager.getmService().downGouJian(req).enqueue(new Callback<List<BuildEntryRes>>() {
            @Override
            public void onResponse(Call<List<BuildEntryRes>> call, Response<List<BuildEntryRes>> response) {
                if (response.body() == null) {
                    showToast("账户登录过期，请退出账户后重新登录");
                    return;
                }

                PreferenceUtils.putString(DetectEntryInfoActivity.this, PreferenceUtils.Key.DOWNGOU,
                        new Gson().toJson(response.body()));
                getQiaoMian(req);
            }

            @Override
            public void onFailure(Call<List<BuildEntryRes>> call, Throwable t) {

            }
        });

    }

    private void getQiaoMian(final SearchCodeReq req) {
        ApiManager.getmService().qiaomian(req).enqueue(new Callback<List<BuildEntryRes>>() {
            @Override
            public void onResponse(Call<List<BuildEntryRes>> call, Response<List<BuildEntryRes>> response) {
                if (response.body() == null) {
                    showToast("账户登录过期，请退出账户后重新登录");
                    return;
                }

                PreferenceUtils.putString(DetectEntryInfoActivity.this, PreferenceUtils.Key.QIAOMIAN,
                        new Gson().toJson(response.body()));
                getDanDu(req);
            }

            @Override
            public void onFailure(Call<List<BuildEntryRes>> call, Throwable t) {

            }
        });
    }

    private void getDanDu(SearchCodeReq req) {
        ApiManager.getmService().dandu(req).enqueue(new Callback<List<BuildEntryRes>>() {
            @Override
            public void onResponse(Call<List<BuildEntryRes>> call, Response<List<BuildEntryRes>> response) {
                if (response.body() == null) {
                    showToast("账户登录过期，请退出账户后重新登录");
                    return;
                }

                PreferenceUtils.putString(DetectEntryInfoActivity.this, PreferenceUtils.Key.DANDU,
                        new Gson().toJson(response.body()));
                getBing();
            }

            @Override
            public void onFailure(Call<List<BuildEntryRes>> call, Throwable t) {

            }
        });
    }

    //获取病害类型数据
    private void getBing() {
        ApiManager.getmService().binghai().enqueue(new Callback<List<BinghaiRes>>() {
            @Override
            public void onResponse(Call<List<BinghaiRes>> call, Response<List<BinghaiRes>> response) {
                if (response.body() == null) {
                    showToast("账户登录过期，请退出账户后重新登录");
                    return;
                }

                PreferenceUtils.putString(DetectEntryInfoActivity.this, PreferenceUtils.Key.BINGHAI,
                        new Gson().toJson(response.body()));
                showToast("数据下载完成");
                ifDownload = true;
            }

            @Override
            public void onFailure(Call<List<BinghaiRes>> call, Throwable t) {

            }
        });
    }

    private void setDetectInfo() {
        //从先前的Activity中获取检测任务数据
        String detectJson = getIntent().getStringExtra("detect");
        info = new Gson().fromJson(detectJson, DetectMission.class);

        List<SimpleText> texts = new ArrayList<>();
        texts.add(new SimpleText("任务代码", info.getJcrw_id()));
        rwid = info.getJcrw_id();
        bridgeCode = info.getQldm();
        texts.add(new SimpleText("桥梁代码", info.getQldm()));
        texts.add(new SimpleText("桥梁名称", info.getQlmc()));
        texts.add(new SimpleText("发布人员", info.getRwfbry()));
        texts.add(new SimpleText("发布时间", info.getRwfbsj()));
        texts.add(new SimpleText("状态", CommonUtils.getStatus(info.getStatus())));
        texts.add(new SimpleText("接收时间", info.getRejssj()));
        texts.add(new SimpleText("接收人员", info.getRwjsry()));
        texts.add(new SimpleText("完成时间", info.getRwwcsj()));
        String bz = info.getBz();
        if (bz.equals("None")) {
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
