package com.magicalrice.adolph.androidtest.test_demo.library_study_demo.appbarlayout_collapsingtoolbarlayout_coordinatorlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;

import com.magicalrice.adolph.androidtest.R;
import com.magicalrice.adolph.androidtest.base_core.BaseActivity;

/**
 * Created by Adolph on 2018/2/2.
 */

public class AppbarLayoutCoordinatorLayoutExampleActivity extends BaseActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private CardView cardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewCompat.setTransitionName(cardView, "card");
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_example_appbarlayout_coordinatorlayout;
    }

    @Override
    protected void initUI() {
        cardView = findViewById(R.id.card_view);
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

    @Override
    protected void setBase() {

    }
}
