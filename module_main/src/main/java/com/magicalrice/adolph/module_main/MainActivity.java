package com.magicalrice.adolph.module_main;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.magicalrice.adolph.common.base.AppManager;
import com.magicalrice.adolph.common.base.BaseActivity;
import com.magicalrice.adolph.common.base.RouterTable;

/**
 * Created by Adolph on 2018/1/18.
 */

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String[] routers = {RouterTable.ITEM_WIDGET_DRAGFLOAT,RouterTable.ITEM_LIBRARY_STUDY_APPBARLAYOUT};
    private String[] titles = {"应用内拖动悬浮按钮", "AppBarLayout&CollapsingToolbar组合使用"};

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private RecyclerView mActivityList;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private ActionBarDrawerToggle toggle;
    private long exitTime = 0;
    private TestAdapter adapter;

    @Override
    protected void initToolbar() {
        super.initToolbar();
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.LEFT);
        collapsingToolbarLayout.setExpandedTitleGravity(Gravity.LEFT | Gravity.BOTTOM);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.main_activity;
    }

    @Override
    protected void initUI() {
        mActivityList = findViewById(R.id.test_demo_list);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        drawerLayout = findViewById(R.id.draw_layout);
        navigationView = findViewById(R.id.nav_menu);
    }

    @Override
    protected void initData() {
        adapter = new TestAdapter();
        mActivityList.setLayoutManager(new LinearLayoutManager(this));
        mActivityList.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected String getDemoName() {
        return "主界面";
    }

    @Override
    protected void setBase() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.test1) {
            showLongToast(item.getTitle().toString());
        }
        return false;
    }

    public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {
        @Override
        public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.main_item_recycler, parent, false);
            TestViewHolder holder = new TestViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(TestViewHolder holder, int position) {
            holder.tv_demo.setText(titles[position]);
            holder.ll_demo.setOnClickListener(v -> {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, v, "card");
                ARouter.getInstance().build(routers[position]).withOptionsCompat(options).navigation(MainActivity.this, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {
                        showShortToast("发现目标Activity");
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        showShortToast("没有目标Activity");
                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        showShortToast("跳转完成");
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {
                        showShortToast("已被拦截");
                    }
                });
            });
        }

        @Override
        public int getItemCount() {
            return routers.length;
        }

        class TestViewHolder extends RecyclerView.ViewHolder {
            private TextView tv_demo;
            private CardView ll_demo;

            public TestViewHolder(View itemView) {
                super(itemView);
                tv_demo = itemView.findViewById(R.id.tv_demo);
                ll_demo = itemView.findViewById(R.id.item_card_demo);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                AppManager.getInstance().exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}