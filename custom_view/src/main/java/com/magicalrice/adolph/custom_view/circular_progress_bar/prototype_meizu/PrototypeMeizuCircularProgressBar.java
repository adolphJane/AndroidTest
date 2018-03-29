package com.magicalrice.adolph.custom_view.circular_progress_bar.prototype_meizu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.magicalrice.adolph.common.utils.ScreenUtils;

/**
 * Created by Adolph on 2018/3/29.
 */

public class PrototypeMeizuCircularProgressBar extends View {
    private Paint mPaint;
    private int mWidth,mHeight;
    private float mRoundC;

    public PrototypeMeizuCircularProgressBar(Context context) {
        this(context,null);
    }

    public PrototypeMeizuCircularProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PrototypeMeizuCircularProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = getLengthSpec(widthMeasureSpec);
        mHeight = getLengthSpec(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(0,mHeight / 4,mWidth,mHeight / 2);
        mPaint.setStrokeWidth(ScreenUtils.dp2px(getContext(),2));
        canvas.drawRoundRect(rectF,mRoundC,mRoundC,mPaint);
        canvas.drawText("下载",mWidth / 2 ,mHeight / 2 ,mPaint);
    }

    private void initPaint() {

    }

    private int getLengthSpec(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        int length = 0;
        if (mode == MeasureSpec.AT_MOST) {
            length = ScreenUtils.dp2px(getContext(),50);
        } else if (mode == MeasureSpec.EXACTLY) {
            length = size;
        }
        return length;
    }

    enum ProgressState {
        NORMAL,DOWNLOADING,FAILURE,SUCCESS
    }
}
