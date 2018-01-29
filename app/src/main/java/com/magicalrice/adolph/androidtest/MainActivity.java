package com.magicalrice.adolph.androidtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.magicalrice.adolph.androidtest.test_demo.custom_widget.drag_float_view.example.DragFloatViewDisplayActivity;

/**
 * Created by Adolph on 2018/1/18.
 */

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, DragFloatViewDisplayActivity.class));
    }
}
