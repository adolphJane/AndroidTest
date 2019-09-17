package com.magicalrice.view.recyclerview.typeTwo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.magicalrice.view.R;

public class ItemHeader extends RelativeLayout {
    public ItemHeader(Context context) {
        this(context,null);
    }

    public ItemHeader(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ItemHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.item_layout_header,this);
    }
}
