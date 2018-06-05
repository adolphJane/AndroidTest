package com.magicalrice.adolph.module_view.cloud_tag;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Adolph on 2018/4/23.
 */

public class CloudTagContainer extends RelativeLayout {
    private Paint mPaint;

    public CloudTagContainer(Context context) {
        this(context,null);
    }

    public CloudTagContainer(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CloudTagContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                RelativeLayout.LayoutParams st = (RelativeLayout.LayoutParams) child.getLayoutParams();
            }
        }
    }
}
