package com.magicalrice.adolph.androidtest.base;

import android.view.View;
import android.widget.RelativeLayout;

import com.magicalrice.adolph.androidtest.R;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by Adolph on 2018/2/2.
 */

public class DocWebActivity extends BaseActivity {

    private WebView webView;
    String url = "";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_doc_web;
    }

    @Override
    protected void initUI() {
        webView = findViewById(R.id.web_view);
    }

    @Override
    protected void initData() {
        url = getIntent().getStringExtra("doc");
        initWebView();
    }

    private void initWebView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected String getDemoName() {
        return "DocWebView";
    }

    @Override
    protected void setBase() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RelativeLayout rl = (RelativeLayout) webView.getParent();
        rl.removeView(webView);
        webView.destroy();
    }
}
