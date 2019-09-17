package com.magicalrice.common.base;

import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.magicalrice.common.R;
import com.magicalrice.common.databinding.ActivityDocWebBinding;
import com.magicalrice.common.router.RouterTable;

/**
 * Created by Adolph on 2018/2/2.
 */

@Route(path = RouterTable.ITEM_WEB_DOC)
public class DocWebActivity extends BaseActivity<ActivityDocWebBinding> {
    private WebView webView;
    String url = "";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_doc_web;
    }

    @Override
    protected void initUI() {
        webView = binding.webView;
    }

    @Override
    protected void initData() {
        url = getIntent().getStringExtra("path");
        initWebView();
    }

    private void initWebView() {
        webView.loadUrl(url);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected String getDemoName() {
        return "DocWebView";
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
