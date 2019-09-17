package com.magicalrice.library_study.coordinatorlayout;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.magicalrice.common.base.BaseActivity;
import com.magicalrice.library_study.R;

/**
 * Created by Adolph on 2018/2/2.
 */

@Route(path = "/libraryStudy/appbarlayout")
public class CoordinatorLayoutActivity extends BaseActivity {

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
        return R.layout.activity_appbarlayout_coordinatorlayout;
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
}
