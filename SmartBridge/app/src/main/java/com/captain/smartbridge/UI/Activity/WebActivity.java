package com.captain.smartbridge.UI.Activity;

import android.content.pm.ActivityInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.captain.smartbridge.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Captain on 17/8/29.
 */

public class WebActivity extends AbsActivity {
    @BindView(R.id.web_view)
    WebView webView;

    @Override
    protected void setSelfContentView() {
        setContentView(R.layout.activity_web);
    }

    @Override
    protected void prepareDatas() {
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

       //横屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //访问网页
        String id = BaseApplication.getID();
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://120.76.145.143:8080/SmartBridge/threeDimensional/index/"+id);
    }
}
