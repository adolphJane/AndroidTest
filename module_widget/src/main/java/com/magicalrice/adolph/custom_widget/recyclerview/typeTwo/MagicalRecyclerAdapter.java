package com.magicalrice.adolph.custom_widget.recyclerview.typeTwo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.magicalrice.adolph.custom_widget.recyclerview.typeTwo.LoadingFooter.State;

import java.util.ArrayList;
import java.util.List;

public abstract class MagicalRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER_VIEW = Integer.MIN_VALUE;
    private static final int TYPE_FOOTER_VIEW = Integer.MIN_VALUE + 1;
    private List<View> mHeaderView = new ArrayList<>();
    private List<View> mFooterView = new ArrayList<>();
    private List<T> mItemList;
    private RecyclerView mRecycler;

    public MagicalRecyclerAdapter (List<T> mList) {
        this.mItemList = mList;
    }

    public abstract RecyclerView.ViewHolder onCreateBaseViewHolder(@NonNull ViewGroup parent, int viewType);

    public abstract void onBindBaseViewHolder(@NonNull RecyclerView.ViewHolder holder,int position);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType < TYPE_HEADER_VIEW + getHeaderViewsCount()) {
            return new ViewHolder(mHeaderView.get(viewType - TYPE_HEADER_VIEW));
        } else if (viewType >= TYPE_FOOTER_VIEW && viewType < Integer.MAX_VALUE / 2) {
            return new ViewHolder(mFooterView.get(viewType - TYPE_FOOTER_VIEW));
        } else {
            return onCreateBaseViewHolder(parent,viewType - Integer.MAX_VALUE / 2);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemSize = mItemList == null ? 0 : mItemList.size();
        int headerCount = getHeaderViewsCount();
        if (position >= headerCount && position < headerCount + itemSize) {
            onBindBaseViewHolder(holder,position - headerCount);
        } else {
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            if (params != null && params instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) params).setFullSpan(true);
            } else if (params == null && params instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams stParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                stParams.setFullSpan(true);
                holder.itemView.setLayoutParams(stParams);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        int itemCount = mItemList == null ? 0 : mItemList.size();
        int headerViewCount = getHeaderViewsCount();
        if (position < headerViewCount) {
            return TYPE_HEADER_VIEW + position;
        } else if (headerViewCount <= position && position < headerViewCount + itemCount) {
            int itemType = super.getItemViewType(position - headerViewCount);
            if (itemCount > Integer.MAX_VALUE / 2) {
                throw new IllegalArgumentException("your adapter's return value of getViewTypeCount() must < Integer.MAX_VALUE / 2");
            }
            return itemType + Integer.MAX_VALUE / 2;
        } else {
            return TYPE_FOOTER_VIEW + position - headerViewCount - itemCount;
        }
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : getHeaderViewsCount() + getFooterViewsCount() + mItemList.size();
    }

    public int getHeaderViewsCount() {
        return mHeaderView.size();
    }

    public int getFooterViewsCount() {
        return mFooterView.size();
    }

    public boolean isHeader(int position) {
        return getHeaderViewsCount() > 0 && position == 0;
    }

    public boolean isFooter(int position) {
        int lastPosition = getItemCount() - 1;
        return getFooterViewsCount() > 0 && position == lastPosition;
    }

    public void removeHeaderView(View view) {
        mHeaderView.remove(view);
        notifyDataSetChanged();
    }

    public void removeFooterView(View view) {
        mFooterView.remove(view);
        notifyDataSetChanged();
    }

    public View getHeaderView() {
        return getHeaderViewsCount() > 0 ? mHeaderView.get(0) : null;
    }

    public View getFooterView() {
        return getFooterViewsCount() > 0 ? mFooterView.get(0) : null;
    }

    public void addHeaderView(View header) {
        if (header == null) {
            throw new RuntimeException("Header is null.");
        }
        mHeaderView.add(header);
        notifyDataSetChanged();
    }

    public void addFooterView(View footer) {
        if (footer == null) {
            throw new RuntimeException("Footer is null.");
        }
        mFooterView.add(footer);
        notifyDataSetChanged();
    }

    public final void itemRangeChanged(int postionStart,int itemCount) {
        notifyItemRangeChanged(postionStart + getHeaderViewsCount(),itemCount);
    }

    public final void itemRangeInserted(int postionStart,int itemCount) {
        notifyItemRangeInserted(postionStart + getHeaderViewsCount(),itemCount);
    }

    public final void itemRangeRemoved(int postionStart,int itemCount) {
        notifyItemRangeRemoved(postionStart + getHeaderViewsCount(),itemCount);
    }

    public final void itemRangeMoved(int fromPosition,int toPostion,int itemCount) {
        notifyItemRangeChanged(fromPosition + getHeaderViewsCount(),toPostion + getHeaderViewsCount(),itemCount);
    }

    public @State int getFooterViewState() {
        if (getFooterViewsCount() > 0) {
            LoadingFooter footerView = (LoadingFooter) getFooterView();
            return footerView.getState();
        }
        return LoadingFooter.NORMAL;
    }

    public void setFooterViewState(@State int state) {
        if (getFooterViewsCount() > 0) {
            LoadingFooter footerView = (LoadingFooter) getFooterView();
            footerView.setState(state);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecycler = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecycler = null;
    }

    public void setFooterViewState(int pageSize, @State int state, View.OnClickListener errorListener) {
        if (mItemList.size() < pageSize) {
            return;
        }

        LoadingFooter footerView;
        if (mRecycler != null) {
            if (getFooterViewsCount() > 0) {
                footerView = (LoadingFooter) getFooterView();
                footerView.setState(state);

                if (state == LoadingFooter.NETWORK_ERROR) {
                    footerView.setOnClickListener(errorListener);
                }
                if (mRecycler != null) {
                    mRecycler.scrollToPosition(getItemCount() - 1);
                }
                mRecycler.scrollToPosition(getItemCount() - 1);
            } else {
                footerView = new LoadingFooter(mRecycler.getContext());
                footerView.setState(state);
                if (state == LoadingFooter.NETWORK_ERROR) {
                    footerView.setOnClickListener(errorListener);
                }
                addFooterView(footerView);
                mRecycler.scrollToPosition(getItemCount() - 1);
            }
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public int getBaseLayoutPosition() {
            return getLayoutPosition() - getHeaderViewsCount();
        }

        public int getBaseAdapterPosition() {
            return getAdapterPosition() - getHeaderViewsCount();
        }
    }
}
