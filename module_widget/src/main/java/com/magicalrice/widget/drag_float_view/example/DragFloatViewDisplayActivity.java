package com.magicalrice.widget.drag_float_view.example;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.view.ViewCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.magicalrice.adolph.custom_widget.R;
import com.magicalrice.common.base.BaseActivity;
import com.magicalrice.widget.drag_float_view.view.MagicalFloatingDragView;

/**
 * Created by Adolph on 2018/1/29.
 */

@Route(path = "/widget/dragfloat")
public class DragFloatViewDisplayActivity extends BaseActivity {
    private RelativeLayout rl_view;

    @Override
    protected void initToolbar() {
        super.initToolbar();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_drag_floating_view;
    }

    @Override
    protected void initUI() {
        rl_view = findViewById(R.id.rl_view);
        ViewCompat.setTransitionName(rl_view, "card");
        ImageView img = new ImageView(this);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img.setImageResource(R.mipmap.ic_launcher_round);
        img.setOnClickListener(v -> Toast.makeText(DragFloatViewDisplayActivity.this, "点击了...", Toast.LENGTH_SHORT).show());

        MagicalFloatingDragView.addView(new MagicalFloatingDragView.Builder()
                .setActivity(this)
                .setDefaultLeft(0)
                .setDefaultTop(200)
                .setNeedNearEdge(true)
                .setSize(100)
                .setView(img));
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
