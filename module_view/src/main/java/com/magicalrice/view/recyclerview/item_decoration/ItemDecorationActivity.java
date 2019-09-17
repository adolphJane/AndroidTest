package com.magicalrice.view.recyclerview.item_decoration;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.magicalrice.common.base.BaseActivity;
import com.magicalrice.common.router.RouterTable;
import com.magicalrice.view.R;

@Route(path = RouterTable.ITEM_VIEW_RECYCLERVIEW_ITEMDECORATION,name = "ItemDecoration")
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
