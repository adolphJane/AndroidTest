package com.magicalrice.adolph.custom_view.horizontal_scroll_tag;

import android.support.v4.view.ViewPager;
import android.widget.HorizontalScrollView;

import com.magicalrice.adolph.common.base.BaseActivity;
import com.magicalrice.adolph.custom_view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adolph on 2018/4/24.
 */

public class TagActivity extends BaseActivity {
    private HorizontalScrollTagLayout tagLayout;
    @Override
    protected int getContentViewId() {
        return R.layout.cv_activity_tag;
    }

    @Override
    protected void initUI() {
        tagLayout = findViewById(R.id.tag_layout);
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

    }

    @Override
    protected String getDemoName() {
        return null;
    }

    @Override
    protected void setBase() {

    }
}
