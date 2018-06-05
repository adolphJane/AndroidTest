package com.magicalrice.adolph.module_case.ordering.widget.slidebar;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.magicalrice.adolph.base_utils.utils.ScreenUtils;
import com.magicalrice.adolph.module_case.ordering.widget.Indicator;

/**
 * Created by Adolph on 2018/6/5.
 */

public class TextWidthColorBar extends ColorBar {
    private Indicator indicator;
    private int realWidth = 0;
    private final int SCROLL_BAR_PADDING;

    public TextWidthColorBar(Context context, Indicator indicator, @DrawableRes int drawableResId, int height) {
        super(context, drawableResId, height);
        this.indicator = indicator;
        SCROLL_BAR_PADDING = (int) ScreenUtils.dp2px(context, 10);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        if (position >= indicator.getAdapter().getCount() - 1) {
//            position = indicator.getAdapter().getCount() - 2;
//        }
        if (getTextView(position) == null) {
            return;
        }
        int width1 = getTextWidth(getTextView(position)) - SCROLL_BAR_PADDING;
        int width2 = 0;
        if (getTextView(position + 1) != null) {
            width2 = getTextWidth(getTextView(position + 1)) - SCROLL_BAR_PADDING;
        }

        realWidth = (int) (width1 * (1 - positionOffset) + width2 * (positionOffset));
    }

    @Override
    public int getWidth(int tabWidth) {
        //要求宽度为字宽度减10px

        if (realWidth == 0) {
            if (indicator.getAdapter() != null) {
                TextView textView = getTextView(indicator.getCurrentItem());
                if (textView != null) {
                    realWidth = getTextWidth(textView) - SCROLL_BAR_PADDING;
                }
            }
        }
        return realWidth;
    }

    /**
     * 如果tab不是textView，可以通过重写该方法，返回tab里面的textView。
     *
     * @param position
     * @return
     */
    protected TextView getTextView(int position) {
        View view = indicator.getItemView(position);
        if (view == null) {
            return null;
        }
        TextView textView;
        if (view instanceof TextView) {
            textView = (TextView) view;
        } else {
            textView = (TextView) ((ViewGroup) view).getChildAt(0);
        }
        return textView;
    }

    private int getTextWidth(TextView textView) {
        Rect bounds = new Rect();
        String text = textView.getText().toString();
        Paint paint = textView.getPaint();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int width = bounds.left + bounds.width();
        return width;
    }
}
