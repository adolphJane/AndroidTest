package com.magicalrice.widget.recyclerview.item_decoration;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.magicalrice.adolph.custom_widget.R;
import com.magicalrice.common.base.BaseActivity;
import com.magicalrice.common.base.RouterTable;

@Route(path = RouterTable.ITEM_WIDGET_RECYCLERVIEW_ITEMDECORATION,name = "ItemDecoration")
public class ItemDecorationActivity extends BaseActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_item_decoration;
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected String getDemoName() {
        return null;
    }
}
