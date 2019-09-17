package com.magicalrice.view.keyboard.example;

import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.magicalrice.common.base.BaseActivity;
import com.magicalrice.common.router.RouterTable;
import com.magicalrice.view.R;
import com.magicalrice.view.keyboard.view.KeyboardUtils;

/**
 * Created by Adolph on 2018/3/14.
 */
@Route(path = RouterTable.ITEM_VIEW_KEYBOARD)
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
