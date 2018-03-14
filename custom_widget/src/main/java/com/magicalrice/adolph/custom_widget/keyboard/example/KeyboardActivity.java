package com.magicalrice.adolph.custom_widget.keyboard.example;

import android.widget.EditText;

import com.magicalrice.adolph.common.base.BaseActivity;
import com.magicalrice.adolph.custom_widget.R;
import com.magicalrice.adolph.custom_widget.keyboard.view.KeyboardDialogUtils;

/**
 * Created by Adolph on 2018/3/14.
 */

public class KeyboardActivity extends BaseActivity {

    private EditText text;
    private KeyboardDialogUtils utils;

    @Override
    protected void initToolbar() {
        super.initToolbar();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.cw_activity_keyboard;
    }

    @Override
    protected void initUI() {
        isShowToolbar();
        text = findViewById(R.id.edit_input);
    }

    @Override
    protected void initData() {
        utils = new KeyboardDialogUtils(this);
    }

    @Override
    protected void initListener() {
        text.setOnClickListener(v -> utils.show((EditText) v));
    }

    @Override
    protected String getDemoName() {
        return null;
    }

    @Override
    protected void setBase() {

    }
}
