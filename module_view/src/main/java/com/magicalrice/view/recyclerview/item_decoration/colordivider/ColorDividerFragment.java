package com.magicalrice.view.recyclerview.item_decoration.colordivider;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.magicalrice.common.base.BaseFragment;
import com.magicalrice.view.R;
import com.magicalrice.view.recyclerview.item_decoration.ItemDecorationAdapter;

import java.util.ArrayList;
import java.util.List;

public class ColorDividerFragment extends BaseFragment {
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
        mRecyclerView.addItemDecoration(new ColorDividerItemDecoration());
    }

    @Override
    protected void initData() {
        for (int i = 0;i < 56;i++) {
            data.add(i + " magic");
        }
    }
}
