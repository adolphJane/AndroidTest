package com.magicalrice.adolph.module_view.horizontal_scroll_tag;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.magicalrice.adolph.base_utils.LogUtils;
import com.magicalrice.adolph.base_utils.utils.ScreenUtils;
import com.magicalrice.adolph.module_view.R;

import java.util.List;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by Adolph on 2018/4/24.
 */

public class HorizontalScrollTagLayout extends HorizontalScrollView {
    private ViewPager viewPager;
    private LinearLayout parentLayout;
    private int selectPosition = 0, lastPosition = -1;
    private onScrollSelectTagListener listener;

    public HorizontalScrollTagLayout(Context context) {
        this(context, null);
    }

    public HorizontalScrollTagLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalScrollTagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parentLayout = new LinearLayout(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        parentLayout.setLayoutParams(params);
        parentLayout.setOrientation(LinearLayout.HORIZONTAL);
        parentLayout.setBackgroundColor(getResources().getColor(R.color.co_white_alpha_1));
        addView(parentLayout);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public void setParam(ViewPager viewPager, List<String> tagList) {
        this.viewPager = viewPager;
        initHeaderTag(tagList);
    }

    private void initHeaderTag(List<String> tagList) {
        if (parentLayout == null) {
            throw new NullPointerException("parentLayout must be initialized.");
        }
        parentLayout.removeAllViews();
        for (int i = 0; i < tagList.size(); i++) {
            TextView textView = new TextView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setPadding((int) ScreenUtils.dp2px(getContext(), 10), (int) ScreenUtils.dp2px(getContext(), 5), (int) ScreenUtils.dp2px(getContext(), 10), (int) ScreenUtils.dp2px(getContext(), 5));
            params.gravity = Gravity.CENTER_VERTICAL;
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setText(tagList.get(i));
            textView.setTextSize(14);
            textView.setTextColor(getResources().getColor(R.color.co_black_alpha_1));
            int finalI = i;
            textView.setOnClickListener(v -> selectItem(finalI));
            parentLayout.addView(textView);
        }
        updateSelectItem();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void selectItem(int position) {
        lastPosition = selectPosition;
        selectPosition = position;
        updateSelectItem();
        updateLastItem();
        if (listener != null) {
            listener.onSelectTag(position);
        }

    }

    private void updateSelectItem() {
        if (parentLayout != null) {
            TextView tv = (TextView) parentLayout.getChildAt(selectPosition);
            if (tv != null) {
                tv.setTextColor(Color.BLACK);
                tv.setTypeface(Typeface.DEFAULT_BOLD);
                int[] position = new int[2];
                tv.getLocationOnScreen(position);
                int dx = 0;
                int halfLayoutWidth = getMeasuredWidth() / 2;
                if (position[0] < halfLayoutWidth) {
                    if (position[0] <= 0) {
                        position[0] = 0;
                    }
                    dx = position[0] - halfLayoutWidth + tv.getWidth() / 2;
                    LogUtils.e("小位置%d,屏宽%d,tv宽度%d", position[0], halfLayoutWidth, tv.getWidth() / 2);
                } else {
                    dx = position[0] - halfLayoutWidth + tv.getWidth() / 2;
                    LogUtils.e("大位置%d,屏宽%d,tv宽度%d", position[0], halfLayoutWidth, tv.getWidth() / 2);
                }
                smoothScrollBy(dx, 0);
            }
        }
    }

    private void updateLastItem() {
        if (parentLayout != null) {
            TextView tv = (TextView) parentLayout.getChildAt(lastPosition);
            if (tv != null) {
                tv.setTextColor(getResources().getColor(R.color.co_black_alpha_1));
                tv.setTypeface(Typeface.DEFAULT);
            }
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        LogUtils.e("horizontal:l%d,t%d,oldL%d,oldT",l,t,oldl,oldt);
        if (listener != null) {
            if (getScrollX() == 0) {
                listener.onScrollTop(true);
                listener.onScrollBottom(false);
            } else if (getScrollX() + getWidth() - getPaddingLeft() - getPaddingRight() == getChildAt(0).getWidth()) {
                listener.onScrollTop(false);
                listener.onScrollBottom(true);
            } else {
                listener.onScrollTop(false);
                listener.onScrollBottom(false);
            }
        }
    }

    public void addOnScrollSelectTagListener(onScrollSelectTagListener listener) {
        this.listener = listener;
    }
}
