package com.magicalrice.adolph.module_main;

import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import com.magicalrice.adolph.base_utils.app_utils.AppManager;
import com.magicalrice.adolph.base_utils.bean.AndroidTestInfo;
import com.magicalrice.adolph.common.base.BaseActivity;
import com.magicalrice.adolph.common.base.RouterTable;
import com.magicalrice.adolph.common.widget.recyclerview.SectionParameters;
import com.magicalrice.adolph.common.widget.recyclerview.SectionedRecyclerViewAdapter;
import com.magicalrice.adolph.common.widget.recyclerview.StatelessSection;
import com.sw.debug.view.modules.TimerModule;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityOptionsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Adolph on 2018/1/18.
 */

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private RecyclerView mActivityList;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private ActionBarDrawerToggle toggle;
    private long exitTime = 0;
    private SectionedRecyclerViewAdapter adapter;

    @Override
    protected void initToolbar() {
        super.initToolbar();
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.LEFT);
        collapsingToolbarLayout.setExpandedTitleGravity(Gravity.LEFT | Gravity.BOTTOM);
        getSupportActionBar().setHomeButtonEnabled(true);                               //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
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
        adapter = new SectionedRecyclerViewAdapter();
        adapter.addSection(new ExpandableTestSection("自定义动画",getCustomAnimationList()));
        adapter.addSection(new ExpandableTestSection("自定义视图",getCustomViewList()));
        adapter.addSection(new ExpandableTestSection("自定义控件",getCustomWidgetList()));
        adapter.addSection(new ExpandableTestSection("游戏",getGameList()));
        adapter.addSection(new ExpandableTestSection("第三方库学习",getLibraryStudyList()));
        adapter.addSection(new ExpandableTestSection("实战案例",getCaseList()));
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (adapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 2;
                    default:
                        return 1;
                }
            }
        });
        mActivityList.setLayoutManager(manager);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.test1) {
            showLongToast(item.getTitle().toString());
        }
        return false;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            TimerModule.Companion.getInstance().begin(getApplicationContext());
        }
    }

    private List<AndroidTestInfo> getCustomAnimationList() {
        List<AndroidTestInfo> list = new ArrayList<>();
        list.add(new AndroidTestInfo("LayoutTransition使用","",RouterTable.ITEM_ANIMARION_LAYOUT_TRANSITION));
        return list;
    }

    private List<AndroidTestInfo> getCustomViewList() {
        List<AndroidTestInfo> list = new ArrayList<>();
        list.add(new AndroidTestInfo("圆形下载进度条","",RouterTable.ITEM_VIEW_PROGRESS_BAR));
        list.add(new AndroidTestInfo("横向滚动标签栏","",RouterTable.ITEM_VIEW_HORIZONTAL_SCROLL_TAG));
        list.add(new AndroidTestInfo("数据统计图","",RouterTable.ITEM_VIEW_DATA_GRAPH));
        return list;
    }

    private List<AndroidTestInfo> getCustomWidgetList() {
        List<AndroidTestInfo> list = new ArrayList<>();
        list.add(new AndroidTestInfo("应用内拖动悬浮按钮","",RouterTable.ITEM_WIDGET_DRAGFLOAT));
        list.add(new AndroidTestInfo("通知栏","",RouterTable.ITEM_WIDGET_NOTIFICATION));
        list.add(new AndroidTestInfo("自定义键盘","",RouterTable.ITEM_WIDGET_KEYBOARD));
        list.add(new AndroidTestInfo("自定义Recyclerview","",RouterTable.ITEM_WIDGET_RECYCLERVIEW));
        list.add(new AndroidTestInfo("自定义ItemDecoration","",RouterTable.ITEM_WIDGET_RECYCLERVIEW_ITEMDECORATION));
        return list;
    }

    private List<AndroidTestInfo> getGameList() {
        List<AndroidTestInfo> list = new ArrayList<>();
        list.add(new AndroidTestInfo("贪吃蛇","",RouterTable.ITEM_GAME_SNAKE));
        return list;
    }

    private List<AndroidTestInfo> getLibraryStudyList() {
        List<AndroidTestInfo> list = new ArrayList<>();
        list.add(new AndroidTestInfo("AppBarLayout&CollapsingToolbar组合使用","",RouterTable.ITEM_LIBRARY_STUDY_APPBARLAYOUT));
        return list;
    }

    private List<AndroidTestInfo> getCaseList() {
        List<AndroidTestInfo> list = new ArrayList<>();
        return list;
    }

    private class ExpandableTestSection extends StatelessSection {

        private String title;
        private List<AndroidTestInfo> list;
        private boolean isExpendable = false;

        public ExpandableTestSection(String title, List<AndroidTestInfo> list) {
            super(SectionParameters.builder()
                    .itemResourceId(R.layout.section_item_test)
                    .headerResourceId(R.layout.section_header_test)
                    .build());
            this.title = title;
            this.list = list;
        }

        @Override
        public int getContentItemsTotal() {
            return isExpendable ? list.size() : 0;
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            itemViewHolder.tvTitle.setText(list.get(position).getName());
            itemViewHolder.tvContent.setText(list.get(position).getContent());
            itemViewHolder.rootView.setOnClickListener(v -> {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, v, "card");
                ARouter.getInstance().build(list.get(position).getPath()).withOptionsCompat(options).navigation(MainActivity.this, new NavigationCallback() {
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
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            final HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;

            headerViewHolder.tvTitle.setText(title);
            headerViewHolder.imgArrow.setImageResource(isExpendable ? R.drawable.co_ic_expand_less_black_24dp : R.drawable.co_ic_expand_more_black_24dp);
            headerViewHolder.rootView.setOnClickListener(v -> {
                isExpendable = !isExpendable;
                adapter.notifyDataSetChanged();
            });
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private View rootView;
        private TextView tvTitle,tvContent;

        public ItemViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        private View rootView;
        private TextView tvTitle;
        private ImageView imgArrow;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            tvTitle = itemView.findViewById(R.id.tvTitle);
            imgArrow = itemView.findViewById(R.id.imgArrow);
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
