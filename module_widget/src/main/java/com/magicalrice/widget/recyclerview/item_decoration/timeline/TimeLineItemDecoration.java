package com.magicalrice.widget.recyclerview.item_decoration.timeline;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.magicalrice.adolph.custom_widget.R;
import com.magicalrice.base_libs.utils.ScreenUtils;

public class TimeLineItemDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private float mOffsetLeft;
    private float mOffsetTop;
    private float mNodeRadius;

    private Bitmap mIcon;

    public TimeLineItemDecoration(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);

        mOffsetLeft = ScreenUtils.dp2px(context,32f);
        mNodeRadius = ScreenUtils.dp2px(context,8f);

        mIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_clock);
        mIcon = Bitmap.createScaledBitmap(mIcon,(int)mNodeRadius * 2,(int)mNodeRadius * 2,false);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.top = 1;
            mOffsetTop = 1;
        }

        outRect.left = (int) mOffsetLeft;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount;i++) {
            View view = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(view);
            float dividerTop;
            if (index == 0) {
                dividerTop = view.getTop();
            } else {
                dividerTop = view.getTop() - mOffsetTop;
            }

            float dividerLeft = parent.getPaddingLeft();
            float dividerRight = view.getWidth() - parent.getPaddingRight();
            float dividerBottom = view.getBottom();

            float centerX = dividerLeft + mOffsetLeft / 2;
            float centerY = dividerTop + (dividerBottom - dividerTop) / 2;

            float upLineTopX = centerX;
            float upLineTopY = dividerTop;
            float upLineBottomX = centerX;
            float upLineBottomY = centerY - mNodeRadius;

            c.drawLine(upLineTopX,upLineTopY,upLineBottomX,upLineBottomY,mPaint);

            c.drawBitmap(mIcon,centerX - mNodeRadius,centerY - mNodeRadius,mPaint);

            float downLineTopX = centerX;
            float downLineTopY = centerY + mNodeRadius;
            float downLineBottomX = centerX;
            float downLineBottomY = dividerBottom;

            c.drawLine(downLineTopX,downLineTopY,downLineBottomX,downLineBottomY,mPaint);
        }
    }
}
