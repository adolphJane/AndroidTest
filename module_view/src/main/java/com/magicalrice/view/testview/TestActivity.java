package com.magicalrice.view.testview;

import android.view.View;
import android.widget.Button;

import com.magicalrice.common.base.BaseActivity;
import com.magicalrice.view.R;

/**
 * Created by Adolph on 2018/3/28.
 */

public class TestActivity extends BaseActivity implements View.OnClickListener {

    private TestView testView;
    private Button btnStart;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initUI() {
        testView = findViewById(R.id.test_view);
        btnStart = findViewById(R.id.btn_test);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        btnStart.setOnClickListener(this);
    }

    @Override
    protected String getDemoName() {
        return null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_test) {
            testView.start();
        }
    }
}
