package com.magicalrice.view.recyclerview.item_decoration.bookrank;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.magicalrice.common.base.BaseFragment;
import com.magicalrice.view.R;

import java.util.ArrayList;
import java.util.List;

public class BookRankFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private List<String> data = new ArrayList<>();
    private BookRankAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item_decoration;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.dividerRecyclerview);
        int resouces[] = new int[] {R.drawable.img_book_renmin,R.drawable.img_book_huochetou,
                R.drawable.img_book_jieyouzahuodian,R.drawable.img_book_tensoflow,R.drawable.img_book_wangyangming
                ,R.drawable.img_book_renmin,R.drawable.img_book_huochetou,
                R.drawable.img_book_jieyouzahuodian,R.drawable.img_book_tensoflow,R.drawable.img_book_wangyangming
                ,R.drawable.img_book_renmin,R.drawable.img_book_huochetou,
                R.drawable.img_book_jieyouzahuodian,R.drawable.img_book_tensoflow,R.drawable.img_book_wangyangming
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
