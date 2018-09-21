package com.magicalrice.adolph.custom_widget.recyclerview.item_decoration.bookrank;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.magicalrice.adolph.common.base.BaseFragment;
import com.magicalrice.adolph.custom_widget.R;
import com.magicalrice.adolph.custom_widget.recyclerview.item_decoration.ItemDecorationAdapter;
import com.magicalrice.adolph.custom_widget.recyclerview.item_decoration.timeline.TimeLineItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class BookRankFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private List<String> data = new ArrayList<>();
    private BookRankAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.w_fragment_item_decoration;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.dividerRecyclerview);
        int resouces[] = new int[] {R.drawable.w_img_book_renmin,R.drawable.w_img_book_huochetou,
                R.drawable.w_img_book_jieyouzahuodian,R.drawable.w_img_book_tensoflow,R.drawable.w_img_book_wangyangming
                ,R.drawable.w_img_book_renmin,R.drawable.w_img_book_huochetou,
                R.drawable.w_img_book_jieyouzahuodian,R.drawable.w_img_book_tensoflow,R.drawable.w_img_book_wangyangming
                ,R.drawable.w_img_book_renmin,R.drawable.w_img_book_huochetou,
                R.drawable.w_img_book_jieyouzahuodian,R.drawable.w_img_book_tensoflow,R.drawable.w_img_book_wangyangming
        };
        adapter = new BookRankAdapter(data,resouces);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new FlagItemDecoration(getActivity()));
    }

    @Override
    protected void initData() {
        data.add("人民的名义- ￥ 33.5");
        data.add("火车头 - ￥ 27.5");
        data.add("解忧杂货店- ￥ 19.9");
        data.add("TensorFlow - ￥ 102.5");
        data.add("王阳明心学 - ￥ 60");

        data.add("人民的名义1- ￥ 33.5");
        data.add("火车头1 - ￥ 27.5");
        data.add("解忧杂货店1- ￥ 19.9");
        data.add("TensorFlow1 - ￥ 102.5");
        data.add("王阳明心学1 - ￥ 60");

        data.add("人民的名义2 - ￥ 33.5");
        data.add("火车头2 - ￥ 27.5");
        data.add("解忧杂货店2- ￥ 19.9");
        data.add("TensorFlow2 - ￥ 102.5");
        data.add("王阳明心学2 - ￥ 60");
    }
}
