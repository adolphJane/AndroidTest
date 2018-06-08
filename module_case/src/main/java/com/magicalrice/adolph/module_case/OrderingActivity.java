package com.magicalrice.adolph.module_case;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;

import com.magicalrice.adolph.common.base.BaseActivity;

/**
 * Created by Adolph on 2018/6/5.
 */

public class OrderingActivity extends BaseActivity implements OnAppBarListener{
    private final String[] TITLES = {
            "点餐",
            "评论",
            "商家"
    };
    private final int DEFAULT_SELECTED_TAB = 0;//默认选中
    private final int PAGE_COUNT = TITLES.length;
    private AppBarLayout mAppBarLayout;
    private AppBarLayoutScrollBehavior mAppBarBehavior;

    @Override
    protected int getContentViewId() {
        return R.layout.c_activity_ordering;
    }

    @Override
    protected void initUI() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.argb(100, 0, 0, 0));
        }

        initAppBar();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected String getDemoName() {
        return "订餐页面";
    }

    @Override
    protected void setBase() {

    }

    private void initAppBar() {
        mAppBarLayout = findViewById(R.id.appbar_layout);
        mAppBarBehavior = new AppBarLayoutScrollBehavior(this,this);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        params.setBehavior(mAppBarBehavior);
        mAppBarLayout.setLayoutParams(params);
    }

    @Override
    public void onTitleBarItemClick(int id) {

    }

    @Override
    public void initEnd(int miniTopHeight) {

    }
}
