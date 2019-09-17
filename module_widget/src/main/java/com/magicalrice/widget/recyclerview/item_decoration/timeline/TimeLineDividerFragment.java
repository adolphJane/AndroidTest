package com.magicalrice.widget.recyclerview.item_decoration.timeline;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.magicalrice.adolph.custom_widget.R;
import com.magicalrice.common.base.BaseFragment;
import com.magicalrice.widget.recyclerview.item_decoration.ItemDecorationAdapter;

import java.util.ArrayList;
import java.util.List;

public class TimeLineDividerFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private List<String> data = new ArrayList<>();
    private ItemDecorationAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item_decoration;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.dividerRecyclerview);
        adapter = new ItemDecorationAdapter(data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new TimeLineItemDecoration(getActivity()));
    }

    @Override
    protected void initData() {
        for (int i = 0;i < 56;i++) {
            data.add(i + " magic");
        }
    }
}
