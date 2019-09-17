package com.magicalrice.widget.recyclerview.item_decoration.header;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.magicalrice.base_libs.utils.ScreenUtils;
import com.magicalrice.widget.recyclerview.item_decoration.SectionInfo;

public class SectionItemDecoration extends RecyclerView.ItemDecoration {
    private SectionInfoCallback mCallback;
    private int mHeaderHeight;
    private int mDividerHeight;

    private TextPaint mTextPaint;
    private Paint mPaint;
    private float mTextSize;
    private Paint.FontMetrics mFontMetrics;

    private float mTextOffsetX;

    public SectionItemDecoration(Context context,SectionInfoCallback callback) {
        this.mCallback = callback;
        mDividerHeight = (int) ScreenUtils.dp2px(context,2f);
        mHeaderHeight = (int) ScreenUtils.dp2px(context,24f);
        mTextSize = (int) ScreenUtils.sp2px(context,22f);

        mHeaderHeight = (int) Math.max(mHeaderHeight,mTextSize);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(mTextSize);
        mFontMetrics = mTextPaint.getFontMetrics();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.YELLOW);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (mCallback != null) {
            SectionInfo sectionInfo = mCallback.getSectionInfo(position);

            if (sectionInfo != null && sectionInfo.isFirstViewInGroup()) {
                outRect.top = mHeaderHeight;
            } else {
                outRect.top = mDividerHeight;
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();

        for (int i = 0;i < childCount;i++) {
            View view = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(view);
            if (mCallback != null) {
                SectionInfo info = mCallback.getSectionInfo(index);
                if (info.isFirstViewInGroup()) {
                    int left = parent.getPaddingLeft();
                    int top = view.getTop() - mHeaderHeight;
                    int right = parent.getWidth() - parent.getPaddingRight();
                    int bottom = view.getTop();

                    c.drawRect(left,top,right,bottom,mPaint);

                    float titleX = left + mTextOffsetX;
                    float titleY = bottom - mFontMetrics.descent;
                    c.drawText(info.getTitle(),titleX,titleY,mTextPaint);
                }
            }
        }
    }

    public interface SectionInfoCallback {
        SectionInfo getSectionInfo(int position);
    }
}