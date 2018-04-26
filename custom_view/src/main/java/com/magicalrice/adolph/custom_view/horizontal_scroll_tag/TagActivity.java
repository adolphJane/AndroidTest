package com.magicalrice.adolph.custom_view.horizontal_scroll_tag;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.magicalrice.adolph.common.base.BaseActivity;
import com.magicalrice.adolph.common.base.RouterTable;
import com.magicalrice.adolph.custom_view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adolph on 2018/4/24.
 */
@Route(path = RouterTable.ITEM_VIEW_HORIZONTAL_SCROLL_TAG)
public class TagActivity extends BaseActivity implements onScrollSelectTagListener {
    private HorizontalScrollTagLayout tagLayout;
    private View leftView,rightView;
    @Override
    protected int getContentViewId() {
        return R.layout.cv_activity_tag;
    }

    @Override
    protected void initUI() {
        tagLayout = findViewById(R.id.tag_layout);
        leftView = findViewById(R.id.left_view);
        rightView = findViewById(R.id.right_view);
    }

    @Override
    protected void initData() {
        List<String> tagList = new ArrayList<>();
        tagList.add("流行");
        tagList.add("高分");
        tagList.add("最近上映");
        tagList.add("动作");
        tagList.add("科幻");
        tagList.add("冒险");
        tagList.add("爱情");
        tagList.add("动漫");
        tagList.add("剧情");
        tagList.add("动作");
        tagList.add("剧情");
        tagList.add("动作");
        tagList.add("剧情");
        tagList.add("科幻");
        tagList.add("最近上映");
        tagList.add("剧情");
        tagList.add("剧情");
        tagList.add("科幻");
        tagList.add("最近上映");
        tagList.add("剧情");
        tagList.add("高分");
        tagList.add("科幻");
        tagList.add("剧情");
        tagLayout.setParam(new ViewPager(this),tagList);
    }

    @Override
    protected void initListener() {
        tagLayout.addOnScrollSelectTagListener(this);
    }

    @Override
    protected String getDemoName() {
        return null;
    }

    @Override
    protected void setBase() {

    }

    @Override
    public void onSelectTag(int position) {

    }

    @Override
    public void onScrollTop(boolean isTop) {
        if (isTop) {
            leftView.setVisibility(View.GONE);
        } else {
            leftView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onScrollBottom(boolean isBottom) {
        if (isBottom) {
            rightView.setVisibility(View.GONE);
        } else {
            rightView.setVisibility(View.VISIBLE);
        }
    }
}
