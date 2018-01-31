package com.magicalrice.adolph.androidtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.magicalrice.adolph.androidtest.base.BaseActivity;
import com.magicalrice.adolph.androidtest.test_demo.custom_widget.drag_float_view.example.DragFloatViewDisplayActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adolph on 2018/1/18.
 */

public class MainActivity extends BaseActivity {

    private TestAdapter adapter;
    private RecyclerView mActivityList;
    private List<Class<?>> activityDemos;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI() {
        mActivityList = findViewById(R.id.test_demo_list);
    }

    @Override
    protected void initData() {
        activityDemos = new ArrayList<>();
        adapter = new TestAdapter();
        mActivityList.setAdapter(adapter);
        activityDemos.add(DragFloatViewDisplayActivity.class);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setBase() {

    }

    public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {
        @Override
        public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_recycler_main, parent);
            TestViewHolder holder = new TestViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(TestViewHolder holder, int position) {
            holder.tv_demo.setText(activityDemos.get(position).getName());
            holder.ll_demo.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, activityDemos.get(position))));
        }

        @Override
        public int getItemCount() {
            return activityDemos.size();
        }

        class TestViewHolder extends RecyclerView.ViewHolder {
            private TextView tv_demo;
            private LinearLayout ll_demo;

            public TestViewHolder(View itemView) {
                super(itemView);
                tv_demo = itemView.findViewById(R.id.tv_demo);
                ll_demo = itemView.findViewById(R.id.ll_demo);
            }
        }
    }
}
