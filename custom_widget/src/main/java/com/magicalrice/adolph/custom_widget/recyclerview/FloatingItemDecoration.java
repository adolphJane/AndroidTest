package com.magicalrice.adolph.custom_widget.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adolph on 2018/1/29.
 */

public class FloatingItemDecoration extends RecyclerView.ItemDecoration {

    private float mTextBaselineOffset, mTextHeight;
    private Context mContext;
    private Paint mTextPaint, mBackgroundPaint;
    private Drawable mDivider;
    private int mTitleHeight;
    private int mDividerHeight;
    private int mDividerWidth;
    //Whether the suspension head has been showed when the list is scrolling
    private boolean showFloatingTopHeaderOnScrolling = true;
    private Map<Integer, String> keys = new HashMap<>();

    /**
     * the custom of parting line
     *
     * @param context
     * @param drawableId
     */
    public FloatingItemDecoration(Context context, @DrawableRes int drawableId) {
        mDivider = ContextCompat.getDrawable(context, drawableId);
        this.mDividerHeight = mDivider.getIntrinsicHeight();
        this.mDividerWidth = mDivider.getIntrinsicWidth();
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, mContext.getResources().getDisplayMetrics()));
        mTextPaint.setColor(Color.WHITE);
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTextHeight = fm.bottom - fm.top;
        mTextBaselineOffset = fm.bottom;

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(Color.MAGENTA);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawVertical(c, parent);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (!showFloatingTopHeaderOnScrolling) {
            return;
        }
        int firstVisiblePos = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
        if (firstVisiblePos == RecyclerView.NO_POSITION) {
            return;
        }
        String title = getTitle(firstVisiblePos);
        if (TextUtils.isEmpty(title)) {
            return;
        }
        boolean flag = true;
        if (getTitle(firstVisiblePos + 1) != null && !title.equals(getTitle(firstVisiblePos + 1))) {
            View child = parent.findViewHolderForAdapterPosition(firstVisiblePos).itemView;
            if (child.getTop() + child.getMeasuredHeight() < mTitleHeight) {
                c.save();
                flag = true;
                c.translate(0, child.getTop() + child.getMeasuredHeight() - mTitleHeight);
            }
        }
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int top = parent.getPaddingTop();
        int bottom = top + mTitleHeight;
        c.drawRect(left, top, right, bottom, mBackgroundPaint);
        float x = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, mContext.getResources().getDisplayMetrics());
        float y = bottom - (mTitleHeight - mTextHeight) / 2 - mTextBaselineOffset;
        c.drawText(title, x, y, mTextPaint);
        if (flag) {
            c.restore();
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildViewHolder(view).getAdapterPosition();
        if (keys.containsKey(pos)) {
            outRect.set(0, mTitleHeight, 0, 0);
        } else {
            outRect.set(0, mDividerHeight, 0, 0);
        }
    }

    private String getTitle(int position) {
        while (position > 0) {
            if (keys.containsKey(position)) {
                return keys.get(position);
            }
            position--;
        }
        return null;
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int top = 0;
        int bottom = 0;
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            if (!keys.containsKey(params.getViewLayoutPosition())) {
                top = child.getTop() - params.topMargin - mDividerHeight;
                bottom = top + mDividerHeight;
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            } else {
                top = child.getTop() - params.topMargin - mDividerHeight;
                bottom = top + mTitleHeight;
                c.drawRect(left, top, right, bottom, mBackgroundPaint);
                float x = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, mContext.getResources().getDisplayMetrics());
                float y = bottom - (mTitleHeight - mTextHeight) / 2 - mTextBaselineOffset;
                c.drawText(keys.get(params.getViewLayoutPosition()), x, y, mTextPaint);
            }
        }
    }

    public void setShowFloatingTopHeaderOnScrolling(boolean showFloatingTopHeaderOnScrolling) {
        this.showFloatingTopHeaderOnScrolling = showFloatingTopHeaderOnScrolling;
    }

    public void setKeys(Map<Integer, String> keys) {
        this.keys.clear();
        this.keys.putAll(keys);
    }

    public void setTitleHeight(int titleHeight) {
        this.mTitleHeight = titleHeight;
    }
}
