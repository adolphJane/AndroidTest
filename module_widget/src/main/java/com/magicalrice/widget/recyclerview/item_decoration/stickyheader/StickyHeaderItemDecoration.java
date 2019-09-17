package com.magicalrice.widget.recyclerview.item_decoration.stickyheader;

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

public class StickyHeaderItemDecoration extends RecyclerView.ItemDecoration {
    private SectionInfoCallback mCallback;
    private int mHeaderHeight;
    private int mDividerHeight;


    //用来绘制Header上的文字
    private TextPaint mTextPaint;
    private Paint mPaint;
    private float mTextSize;
    private Paint.FontMetrics mFontMetrics;

    private float mTextOffsetX;

    public StickyHeaderItemDecoration(Context context,SectionInfoCallback callback) {
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
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int childCount = parent.getChildCount();

        for (int i = 0;i < childCount;i++) {
            View view = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(view);

            if (mCallback != null) {
                SectionInfo sectionInfo = mCallback.getSectionInfo(index);
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();

                //屏幕上第一个可见的 ItemView 时，i == 0;
                if (i != 0) {
                    //只有组内的第一个ItemView之上才绘制
                    if (sectionInfo.isFirstViewInGroup()) {
                        int top = view.getTop() -  mHeaderHeight;
                        int bottom = view.getTop();
                        drawHeaderRect(c,sectionInfo,left,top,right,bottom);
                    }
                } else {
                    //当 ItemView 是屏幕上第一个可见的View 时，不管它是不是组内第一个View
                    //它都需要绘制它对应的 StickyHeader。

                    // 还要判断当前的 ItemView 是不是它组内的最后一个 View
                    int top = parent.getPaddingTop();
                    if (sectionInfo.isLastViewInGroup()) {
                        int suggestTop = view.getBottom() - mHeaderHeight;
                        // 当 ItemView 与 Header 底部平齐的时候，判断 Header 的顶部是否小于
                        // parent 顶部内容开始的位置，如果小于则对 Header.top 进行位置更新，
                        //否则将继续保持吸附在 parent 的顶部
                        if (suggestTop < top) {
                            top = suggestTop;
                        }
                    }

                    int bottom = top + mHeaderHeight;
                    drawHeaderRect(c,sectionInfo,left,top,right,bottom);
                }
            }
        }
    }

    private void drawHeaderRect(Canvas c, SectionInfo sectionInfo, int left, int top, int right, int bottom) {
        c.drawRect(left,top,right,bottom,mPaint);

        float titleX = left + mTextOffsetX;
        float titleY = bottom - mFontMetrics.descent;

        c.drawText(sectionInfo.getTitle(),titleX,titleY,mTextPaint);
    }

    public interface SectionInfoCallback {
        SectionInfo getSectionInfo(int position);
    }
}
