package com.magicalrice.adolph.androidtest.test_demo.custom_widget.drag_float_view.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.magicalrice.adolph.androidtest.R;
import com.magicalrice.adolph.androidtest.test_demo.custom_widget.drag_float_view.view.MagicalFloatingDragView;
import com.magicalrice.adolph.androidtest.test_demo.custom_widget.drag_float_view.view.MagicalFloatingDragView.Builder;

/**
 * Created by Adolph on 2018/1/29.
 */

public class DragFloatViewDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView img = new ImageView(this);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img.setImageResource(R.mipmap.ic_launcher_round);
        img.setOnClickListener(v -> Toast.makeText(DragFloatViewDisplayActivity.this, "点击了...", Toast.LENGTH_SHORT).show());

        MagicalFloatingDragView.addView(new Builder()
                .setActivity(this)
                .setDefaultLeft(30)
                .setDefaultTop(30)
                .setNeedNearEdge(true)
                .setSize(100)
                .setView(img));
    }
}
