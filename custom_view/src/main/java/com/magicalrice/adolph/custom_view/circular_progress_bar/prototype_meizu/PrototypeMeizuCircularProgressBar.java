package com.magicalrice.adolph.custom_view.circular_progress_bar.prototype_meizu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.magicalrice.adolph.common.utils.ScreenUtils;
import com.magicalrice.adolph.custom_view.R;
import com.orhanobut.logger.Logger;

/**
 * Created by Adolph on 2018/3/29.
 */

public class PrototypeMeizuCircularProgressBar extends View {
    private static final int NORMAL = 100,DOWNLOADING = 101,FAILURE = 102,SUCCESS = 103;
    private Paint mPaint,mTextPaint;
    private int mWidth,mHeight;
    private float mRoundC,mTextSize,mStrokeWidth;
    private int mStrokeColor,mTextColor;
    private String mTextContent;
    @Status
    private int status = NORMAL;

    public PrototypeMeizuCircularProgressBar(Context context) {
        this(context,null);
    }

    public PrototypeMeizuCircularProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PrototypeMeizuCircularProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mStrokeColor);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
    }

    private void initData(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.cv_meizu_circular_progress_bar);
        mRoundC = a.getDimension(R.styleable.cv_meizu_circular_progress_bar_cvRoundAngle,ScreenUtils.dp2px(getContext(),10));
        mTextContent = a.getString(R.styleable.cv_meizu_circular_progress_bar_cvTextContent);
        mTextSize = a.getDimension(R.styleable.cv_meizu_circular_progress_bar_cvTextSize,ScreenUtils.sp2px(getContext(),18));
        mTextColor = a.getColor(R.styleable.cv_meizu_circular_progress_bar_cvTextColor,Color.BLACK);
        mStrokeColor = a.getColor(R.styleable.cv_meizu_circular_progress_bar_cvStrokeColor,Color.WHITE);
        mStrokeWidth = a.getDimension(R.styleable.cv_meizu_circular_progress_bar_cvStrokeWidth,ScreenUtils.dp2px(getContext(),10));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = getLengthSpec(widthMeasureSpec,true);
        mHeight = getLengthSpec(heightMeasureSpec,false);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (status) {
            case NORMAL:
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(ScreenUtils.dp2px(getContext(),2));
                mPaint.setTextAlign(Paint.Align.CENTER);

                RectF rectF = new RectF(0,0,mWidth,mHeight);
                canvas.drawRoundRect(rectF,mRoundC,mRoundC,mPaint);
                Paint.FontMetricsInt metrics = mTextPaint.getFontMetricsInt();
                Logger.e("rectF.bottom:%f rectF.top:%f metrics.bottom:%d metrics.top:%d  mPaint.measureText:%f,rectf.centerX:%f",rectF.bottom,rectF.top,metrics.bottom,metrics.top,mTextPaint.measureText(mTextContent),rectF.centerX());
                float baseLine = rectF.bottom - ((rectF.bottom - rectF.top - metrics.bottom + metrics.top) / 2 + metrics.bottom);
                canvas.drawText(mTextContent,rectF.centerX() - mTextPaint.measureText(mTextContent) / 2 ,baseLine ,mTextPaint);
                break;
            case DOWNLOADING:
                break;
            case SUCCESS:
                break;
            case FAILURE:
                break;
        }
    }

    private int getLengthSpec(int measureSpec,boolean isWidth) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        int length = 0;
        if (mode == MeasureSpec.AT_MOST) {
            if (isWidth) {
                length = (int) ScreenUtils.dp2px(getContext(), 75);
            } else {
                length = (int) ScreenUtils.dp2px(getContext(),35);
            }
        } else if (mode == MeasureSpec.EXACTLY) {
            length = size;
        }
        return length;
    }

    @Status
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @IntDef({NORMAL,DOWNLOADING,SUCCESS,FAILURE})
    public @interface Status {
    }
}
