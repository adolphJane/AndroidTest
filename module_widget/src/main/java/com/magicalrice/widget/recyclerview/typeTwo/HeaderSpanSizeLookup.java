package com.magicalrice.widget.recyclerview.typeTwo;

import androidx.recyclerview.widget.GridLayoutManager;

public class HeaderSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private MagicalRecyclerAdapter mAdapter;
    private int mSpanSize = 1;

    public HeaderSpanSizeLookup(MagicalRecyclerAdapter adapter, int spanSize) {
        this.mAdapter = adapter;
        this.mSpanSize = spanSize;
    }

    @Override
    public int getSpanSize(int position) {
        if (mAdapter == null) {
            throw new RuntimeException("You must setAdapter for RecyclerView first.");
        }

        boolean isHeaderOrFooter = mAdapter.isHeader(position) || mAdapter.isFooter(position);
        return isHeaderOrFooter ? mSpanSize : 1;
    }
}
