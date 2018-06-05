package com.magicalrice.adolph.module_case.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magicalrice.adolph.module_case.R;

/**
 * Created by Adolph on 2018/6/5.
 */

public class CommentFragment extends Fragment {
    NestedScrollView mNestedScrollView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.c_fragment_comment, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView(View rootView) {
        mNestedScrollView = rootView.findViewById(R.id.nested_scroll_view);
        mNestedScrollView.setNestedScrollingEnabled(false);
    }

}
