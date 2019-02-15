package com.magicalrice.adolph.custom_widget.recyclerview.typeTwo;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class ExStaggeredGridLayoutManager extends StaggeredGridLayoutManager {
    private final String TAG = getClass().getSimpleName();
    private GridLayoutManager.SpanSizeLookup mSpanSizeup;

    public ExStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    public GridLayoutManager.SpanSizeLookup getmSpanSizeup() {
        return mSpanSizeup;
    }

    public void setmSpanSizeup(GridLayoutManager.SpanSizeLookup mSpanSizeup) {
        this.mSpanSizeup = mSpanSizeup;
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        for (int i = 0;i < getItemCount();i++) {
            if (mSpanSizeup.getSpanSize(i) > 1) {
                try {
                    View view = recycler.getViewForPosition(i);
                    if (view != null) {
                        StaggeredGridLayoutManager.LayoutParams params = (LayoutParams) view.getLayoutParams();
                        params.setFullSpan(true);
                    }
                } catch (Exception e ) {
                    e.printStackTrace();
                }
            }
        }
        super.onMeasure(recycler, state, widthSpec, heightSpec);
    }
}
