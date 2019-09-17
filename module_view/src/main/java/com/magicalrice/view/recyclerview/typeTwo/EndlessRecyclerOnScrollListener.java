package com.magicalrice.view.recyclerview.typeTwo;

import android.view.View;

import androidx.annotation.IntDef;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener implements OnListNextPageListener {
    private static final int LinearLayout = 1;
    private static final int GridLayout = 2;
    private static final int StaggeredGridLayout = 3;

    private int currentScrollState = 0;

    private int[] lastPositions;

    private int lastVisibleItemPosition;            //最后一个可见的item的位置

    @LayoutManagerType
    private int type;

    @IntDef({LinearLayout,GridLayout,StaggeredGridLayout})
    public @interface LayoutManagerType{}

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (type == 0) {
            if (layoutManager instanceof LinearLayoutManager) {
                type = LinearLayout;
            } else if (layoutManager instanceof GridLayoutManager) {
                type = GridLayout;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                type = StaggeredGridLayout;
            } else {
                throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        switch (type) {
            case LinearLayout:
                lastVisibleItemPosition = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
                break;
            case GridLayout:
                lastVisibleItemPosition = ((GridLayoutManager)layoutManager).findLastVisibleItemPosition();
                break;
            case StaggeredGridLayout:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }

                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                break;
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        currentScrollState = newState;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        if ((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition >= totalItemCount - 1)) {
            onLoadNextPage(recyclerView);
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }

        return max;
    }

    @Override
    public void onLoadNextPage(View view) {

    }
}
