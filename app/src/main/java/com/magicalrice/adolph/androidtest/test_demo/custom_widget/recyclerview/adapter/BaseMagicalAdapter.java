package com.magicalrice.adolph.androidtest.test_demo.custom_widget.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adolph on 2018/1/30.
 */

public abstract class BaseMagicalAdapter<D, T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    protected static final String TAG = "BaseMagicalAdapter";

    protected Context mContext;
    protected List<D> mData;
    protected int layoutResId;
    protected LayoutInflater mInflater;
    //header \ footer layout
    private LinearLayout mHeaderLayout, mFooterLayout;
    //the recyclerview need to be binded
    private RecyclerView mRecyclerView;

    public BaseMagicalAdapter(@LayoutRes int layoutResId, @Nullable List<D> data) {
        this.mData = data == null ? new ArrayList<D>() : data;
        if (layoutResId != 0) {
        }
    }

    public BaseMagicalAdapter(@LayoutRes int layoutResId) {
        this(layoutResId, null);
    }

    public BaseMagicalAdapter(@Nullable List<D> data) {
        this(0, data);
    }

    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    private void setRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    /**
     * add a new data into certain location
     *
     * @param position
     * @param data
     */
    public void addData(@IntRange(from = 0) int position, @NonNull D data) {
        mData.add(position, data);
        notifyItemInserted(position);
    }

    /**
     * add a new data
     *
     * @param data
     */
    public void addData(@NonNull D data) {
        mData.add(data);
        notifyItemInserted(mData.size());
    }

    public void remove(@IntRange(from = 0) int position) {
        mData.remove(position);
    }


}
