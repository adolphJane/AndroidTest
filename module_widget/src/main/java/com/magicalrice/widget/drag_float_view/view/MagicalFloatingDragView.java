package com.magicalrice.widget.drag_float_view.view;

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
    //the view which can drag
    private View mDragView;
    //Builder model
    private Builder mBuilder;
    //the height of status and screen,the width of screen
    private int mStatusBarHeight, mScreenHeight, mScreenWidth;
    private int mStartX, mStartY, mLastX, mLastY;
    //whether consume the event of click
    private boolean mTouchResult = false;

    public static MagicalFloatingDragView addView(Builder builder) {
        MagicalFloatingDragView dragView = new MagicalFloatingDragView(builder);
        return dragView;
    }

    private MagicalFloatingDragView(Builder builder) {
        mActivity = builder.activity;
        mDragView = builder.view;
        mBuilder = builder;
        initDragView();
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
            moveNearEdge();
        }
    }

    /**
     * Initialize the DragView
     */
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
        FrameLayout.LayoutParams params = createLayoutParams(left, mStatusBarHeight + mBuilder.defaultTop, 0, 0);
        FrameLayout root = (FrameLayout) mActivity.getWindow().getDecorView();
        root.addView(mDragView, params);
        mDragView.setOnTouchListener(this);
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
        animator.addUpdateListener(animation -> {
                int leftV = (int) animation.getAnimatedValue();
                mDragView.setLayoutParams(createLayoutParams(leftV, mDragView.getTop(), 0, 0));
        });
        animator.start();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchResult = false;
                mStartX = mLastX = (int) event.getRawX();
                mStartY = mLastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int left, top, right, bottom;
                int dx = (int) (event.getRawX() - mLastX);
                int dy = (int) (event.getRawY() - mLastY);
                left = v.getLeft() + dx;
                if (left < 0) {
                    left = 0;
                }
                right = left + v.getWidth();
                if (right > mScreenWidth) {
                    right = mScreenWidth;
                    left = mScreenWidth - v.getWidth();
                }
                top = v.getTop() + dy;
                if (top < mStatusBarHeight + 2) {
                    top = mStatusBarHeight + 2;
                }
                bottom = top + v.getHeight();
                if (bottom > mScreenHeight) {
                    bottom = mScreenHeight;
                    top = bottom - v.getHeight();
                }
                v.layout(left, top, right, bottom);
                mLastX = (int) event.getRawX();
                mLastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                v.setLayoutParams(createLayoutParams(v.getLeft(), v.getTop(), 0, 0));
                float endX = event.getRawX();
                float endY = event.getRawY();
                if (Math.abs(endX - mStartX) > 5 || Math.abs(endY - mStartY) > 5) {
                    mTouchResult = true;
                }
                if (mTouchResult && mBuilder.needNearEdge) {
                    moveNearEdge();
                }
                break;
        }
        return mTouchResult;
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
        private View view;

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

        public Builder setView(View view) {
            this.view = view;
            return this;
        }
    }
}