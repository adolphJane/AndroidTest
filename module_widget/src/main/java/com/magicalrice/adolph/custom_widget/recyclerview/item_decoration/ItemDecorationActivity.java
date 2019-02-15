package com.magicalrice.adolph.custom_widget.recyclerview.item_decoration;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.magicalrice.adolph.common.base.BaseActivity;
import com.magicalrice.adolph.common.base.RouterTable;
import com.magicalrice.adolph.custom_widget.R;

@Route(path = RouterTable.ITEM_WIDGET_RECYCLERVIEW_ITEMDECORATION,name = "ItemDecoration")
public class ItemDecorationActivity extends BaseActivity{

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
