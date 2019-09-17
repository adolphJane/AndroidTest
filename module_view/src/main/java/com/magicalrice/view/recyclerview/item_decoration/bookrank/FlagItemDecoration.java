package com.magicalrice.view.recyclerview.item_decoration.bookrank;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.magicalrice.base_libs.utils.ScreenUtils;
import com.magicalrice.view.R;

public class FlagItemDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private Bitmap mIcon;
    private float mFlagLeft;

    public FlagItemDecoration(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);

        mIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_hotsale);
        mFlagLeft = ScreenUtils.dp2px(context,60);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = 0;
        } else  {
            outRect.top = 2;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount;i++) {
            View view = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(view);
            float top = view.getTop();
            if (index < 3) {
                c.drawBitmap(mIcon,mFlagLeft,top,mPaint);
            }
        }
    }
}