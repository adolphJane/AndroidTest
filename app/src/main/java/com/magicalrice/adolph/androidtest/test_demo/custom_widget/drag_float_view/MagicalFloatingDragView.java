package com.magicalrice.adolph.androidtest.test_demo.custom_widget.drag_float_view;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;

import java.lang.reflect.Field;

/**
 * Created by Adolph on 2018/1/26.
 */

public class MagicalFloatingDragView implements View.OnTouchListener {

    private Activity mActivity;
    private View mDragView;
    private Builder mBuilder;
    private int mStatusBarHeight, mScreenHeight, mScreenWidth;
    private int mStartX, mStartY, mLastX, mLastY;
    private boolean mTouchResult = false;

    private MagicalFloatingDragView(Builder builder, View view) {
        mActivity = builder.activity;
        mDragView = view;
        mBuilder = builder;

    }

    public View getDragView() {
        return mDragView;
    }

    public boolean getNeedNearEdge() {
        return mBuilder.needNearEdge;
    }

    public void setNeedNearEdge(boolean needNearEdge) {
        mBuilder.needNearEdge = needNearEdge;
        if (mBuilder.needNearEdge) {

        }
    }

    public void initDragView() {
        if (mActivity == null)
            throw new NullPointerException("This activity is null");
        if (mDragView == null)
            throw new NullPointerException("This dragview is null");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && mActivity.isDestroyed())
            return;
        WindowManager wm = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            DisplayMetrics metrics = mActivity.getResources().getDisplayMetrics();
            wm.getDefaultDisplay().getMetrics(metrics);
            mScreenWidth = metrics.widthPixels;
            mScreenHeight = metrics.heightPixels;
        }

        Rect frame = new Rect();
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        mStatusBarHeight = frame.top;
        if (mStatusBarHeight <= 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object obj = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                mStatusBarHeight = mActivity.getResources().getDimensionPixelOffset(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int left = mBuilder.needNearEdge ? 0 : mBuilder.defaultLeft;
//        FrameLayout.LayoutParams params =
    }

    private void moveNearEdge() {
        int left = mDragView.getLeft();
        int lastX;
        if (left + mDragView.getWidth() / 2 <= mScreenWidth / 2) {
            lastX = 0;
        } else {
            lastX = mScreenWidth - mDragView.getWidth();
        }
        ValueAnimator animator = ValueAnimator.ofInt(left, lastX);
        animator.setDuration(1000);
        animator.setRepeatCount(0);
        animator.setInterpolator(new BounceInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    private FrameLayout.LayoutParams createLayoutParams(int left, int top, int right, int bottom) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(mBuilder.size, mBuilder.size);
        layoutParams.setMargins(left, top, right, bottom);
        return layoutParams;
    }

    public static class Builder {
        private Activity activity;
        private int size = FrameLayout.LayoutParams.WRAP_CONTENT;
        private int defaultTop = 0;
        private int defaultLeft = 0;
        private boolean needNearEdge = false;

        public Builder setActivity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder setDefaultTop(int top) {
            this.defaultTop = defaultTop;
            return this;
        }

        public Builder setDefaultLeft(int left) {
            this.defaultLeft = left;
            return this;
        }

        public Builder setNeedNearEdge(boolean needNearEdge) {
            this.needNearEdge = needNearEdge;
            return this;
        }
    }
}