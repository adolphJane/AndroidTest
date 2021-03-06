package com.magicalrice.widget.keyboard.example;

import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.magicalrice.adolph.custom_widget.R;
import com.magicalrice.common.base.BaseActivity;
import com.magicalrice.common.base.RouterTable;
import com.magicalrice.widget.keyboard.view.KeyboardUtils;

/**
 * Created by Adolph on 2018/3/14.
 */
@Route(path = RouterTable.ITEM_WIDGET_KEYBOARD)
public class KeyboardActivity extends BaseActivity {

    private EditText edit;
    private KeyboardUtils utils;
    private ImageView imgDoc;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_keyboard;
    }

    @Override
    protected void initUI() {
        initToolbar();
        edit = findViewById(R.id.edit_input);
        edit.setInputType(InputType.TYPE_NULL);
        imgDoc = findViewById(R.id.img_web);
    }

    @Override
    protected void initData() {
        utils = new KeyboardUtils(this);
    }

    @Override
    protected void initListener() {
        edit.setOnClickListener(v -> utils.attachTo((EditText) v));
        imgDoc.setOnClickListener(v -> ARouter.getInstance().build(RouterTable.ITEM_WEB_DOC).navigation(this));
    }

    @Override
    protected String getDemoName() {
        return null;
    }
}
