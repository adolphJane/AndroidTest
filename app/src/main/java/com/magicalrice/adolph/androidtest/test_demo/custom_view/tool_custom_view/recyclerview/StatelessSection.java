package com.magicalrice.adolph.androidtest.test_demo.custom_view.tool_custom_view.recyclerview;

/**
 * Created by Adolph on 2018/1/26.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Abstract {@link Section} with no states.
 */
public abstract class StatelessSection extends Section {
    public StatelessSection(SectionParameters parameters) {
        super(parameters);
        if (parameters.emptyResourceId != null)
            throw new IllegalArgumentException("Stateless section shouldn`t have a loading state resource");
        if (parameters.failedResourceId != null)
            throw new IllegalArgumentException("Stateless section shouldn`t have a failed state resource");
        if (parameters.loadingResourceId != null)
            throw new IllegalArgumentException("Stateless section shouldn`t have a loading state resource");
    }

    @Override
    public final void onBindEmptyViewHolder(RecyclerView.ViewHolder holder) {
        super.onBindEmptyViewHolder(holder);
    }

    @Override
    public final RecyclerView.ViewHolder getEmptyViewHolder(View view) {
        return super.getEmptyViewHolder(view);
    }

    @Override
    public final void onBindFailedViewHolder(RecyclerView.ViewHolder holder) {
        super.onBindFailedViewHolder(holder);
    }

    @Override
    public final RecyclerView.ViewHolder getFailedViewHolder(View view) {
        return super.getFailedViewHolder(view);
    }

    @Override
    public final void onBindLoadingViewHolder(RecyclerView.ViewHolder holder) {
        super.onBindLoadingViewHolder(holder);
    }

    @Override
    public final RecyclerView.ViewHolder getLoadingViewHolder(View view) {
        return super.getLoadingViewHolder(view);
    }
}
