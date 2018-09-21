package com.magicalrice.adolph.custom_widget.recyclerview.item_decoration.header;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.magicalrice.adolph.common.base.BaseFragment;
import com.magicalrice.adolph.custom_widget.R;
import com.magicalrice.adolph.custom_widget.recyclerview.item_decoration.ItemDecorationAdapter;
import com.magicalrice.adolph.custom_widget.recyclerview.item_decoration.SectionInfo;
import com.magicalrice.adolph.custom_widget.recyclerview.item_decoration.timeline.TimeLineItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class HeaderFragment extends BaseFragment {
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
        SectionItemDecoration.SectionInfoCallback callback = position -> {
            /**
             * 分组逻辑，这里为了测试每5个数据为一组。大家可以在实际开发中
             * 替换为真正的需求逻辑
             */
            int sectionId = position / 5;
            int index = position % 5;
            SectionInfo sectionInfo = new SectionInfo(sectionId,sectionId + "");
            sectionInfo.setPosition(index);
            return sectionInfo;
        };
        mRecyclerView.addItemDecoration(new SectionItemDecoration(getActivity(),callback));
    }

    @Override
    protected void initData() {
        for (int i = 0;i < 56;i++) {
            data.add(i + " magic");
        }
    }
}
