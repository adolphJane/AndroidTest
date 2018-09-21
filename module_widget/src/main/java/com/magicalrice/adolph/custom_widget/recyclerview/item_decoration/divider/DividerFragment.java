package com.magicalrice.adolph.custom_widget.recyclerview.item_decoration.divider;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.magicalrice.adolph.common.base.BaseFragment;
import com.magicalrice.adolph.custom_widget.R;
import com.magicalrice.adolph.custom_widget.recyclerview.item_decoration.ItemDecorationAdapter;

import java.util.ArrayList;
import java.util.List;

public class DividerFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private List<String> data = new ArrayList<>();
    private ItemDecorationAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.w_fragment_item_decoration;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.dividerRecyclerview);
        adapter = new ItemDecorationAdapter(data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration());
    }

    @Override
    protected void initData() {
        for (int i = 0;i < 56;i++) {
            data.add(i + " magic");
        }
    }
}
