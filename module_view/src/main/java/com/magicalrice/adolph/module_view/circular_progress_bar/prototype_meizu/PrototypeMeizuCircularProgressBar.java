package com.magicalrice.adolph.module_view.circular_progress_bar.prototype_meizu;

import android.animation.ValueAnimator;
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

import com.magicalrice.adolph.base_utils.utils.ScreenUtils;
import com.magicalrice.adolph.common.utils.L;

import java.math.BigDecimal;
import com.magicalrice.adolph.module_view.R;

/**
 * Created by Adolph on 2018/3/29.
 */

public class PrototypeMeizuCircularProgressBar extends View {
    public static final int NORMAL = 100,DOWNLOADING = 101,PAUSE = 102,SUCCESS = 103,FAILURE = 104;
    private final float sMultiple = 1f,sMaxMultiple = 2.5f;
    private Paint mPaint,mTextPaint;
    private int mWidth,mHeight,mRaidus,mSweepAngle;
    private float mRoundC,mTextSize,mStrokeWidth,mMultiple = sMaxMultiple;
    private int mProgressColor,mTextColor,mPauseColor,mDefColor;
    private String mTextContent;
    private ValueAnimator mValueAnimatorRect,mValueAnimatorProgress;
    private DownloadProgressListener listener;

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
        mPaint.setColor(mProgressColor);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        setFocusable(false);
    }

    private void initData(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.v_meizu_circular_progress_bar);
        mRoundC = a.getDimension(R.styleable.v_meizu_circular_progress_bar_vRoundAngle,ScreenUtils.dp2px(getContext(),30));
        mTextContent = a.getString(R.styleable.v_meizu_circular_progress_bar_vTextContent);
        mTextSize = a.getDimension(R.styleable.v_meizu_circular_progress_bar_vTextSize,ScreenUtils.sp2px(getContext(),18));
        mTextColor = a.getColor(R.styleable.v_meizu_circular_progress_bar_vTextColor, Color.WHITE);
        mProgressColor = a.getColor(R.styleable.v_meizu_circular_progress_bar_vProgressColor,Color.parseColor("#FF34A350"));
        mDefColor = a.getColor(R.styleable.v_meizu_circular_progress_bar_vDefColor,Color.WHITE);
        mPauseColor = a.getColor(R.styleable.v_meizu_circular_progress_bar_vPauseColor,Color.parseColor("#FF34A350"));
        mStrokeWidth = a.getDimension(R.styleable.v_meizu_circular_progress_bar_vStrokeWidth,ScreenUtils.dp2px(getContext(),10));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = getLengthSpec(widthMeasureSpec,true);
        mHeight = getLengthSpec(heightMeasureSpec,false);
        mRaidus = (int) (Math.min(mWidth,mHeight) / 2 * 0.7);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);
        switch (status) {
            case NORMAL:
                drawRoundRect(canvas);
                break;
            case DOWNLOADING:
                drawPauseTag(canvas);
                drawDefCircle(canvas);
                drawProgressCircle(canvas);
                break;
            case PAUSE:
                break;
            case SUCCESS:
                drawRoundRect(canvas);
                break;
            case FAILURE:
                drawRoundRect(canvas);
                break;
        }
    }

    private void drawTipsText(RectF rectF,Canvas canvas) {
        Paint.FontMetricsInt metrics = mTextPaint.getFontMetricsInt();
        float baseLine = rectF.bottom - ((rectF.bottom - rectF.top - metrics.bottom + metrics.top) / 2 + metrics.bottom);
        canvas.drawText(mTextContent,rectF.centerX() - mTextPaint.measureText(mTextContent) / 2 ,baseLine ,mTextPaint);
    }

    private void drawRoundRect(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(ScreenUtils.dp2px(getContext(),2));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(mProgressColor);
        RectF rectF = new RectF(-mMultiple * mRaidus, -mRaidus,mRaidus * mMultiple,mRaidus);
        canvas.drawRoundRect(rectF,mRoundC,mRoundC,mPaint);
        drawTipsText(rectF,canvas);
    }

    private void drawDefCircle(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(ScreenUtils.dp2px(getContext(),2));
        mPaint.setColor(mDefColor);
        RectF rectF = new RectF(-mRaidus,-mRaidus,mRaidus,mRaidus);
        canvas.drawArc(rectF,0,360,false,mPaint);
    }

    private void drawPauseTag(Canvas canvas) {
        mPaint.setColor(mPauseColor);
        mPaint.setStyle(Paint.Style.FILL);
        RectF rectLeft = new RectF(-mRaidus / 2.6f,-mRaidus / 2.4f, -mRaidus / 8,mRaidus / 2.4f);
        RectF rectRight = new RectF(mRaidus / 8, -mRaidus / 2.4f,mRaidus / 2.6f,mRaidus / 2.4f);
        canvas.drawRect(rectLeft,mPaint);
        canvas.drawRect(rectRight,mPaint);
    }

    private void drawProgressCircle(Canvas canvas) {
        mPaint.setColor(mProgressColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        RectF rectF = new RectF(-mRaidus,-mRaidus,mRaidus,mRaidus);
        canvas.drawArc(rectF,-90,mSweepAngle,false,mPaint);
    }

    private void onDrawRect() {
        if (mValueAnimatorProgress != null) {
            mValueAnimatorProgress.cancel();
        }
        if (mValueAnimatorRect != null) {
            mValueAnimatorRect.start();
        } else {
            mValueAnimatorRect = ValueAnimator.ofFloat(sMultiple,sMaxMultiple);
            mValueAnimatorRect.setDuration(500);
            mValueAnimatorRect.addUpdateListener(animation -> {
                mMultiple = (float) animation.getAnimatedValue();
                invalidate();
            });
            mValueAnimatorRect.start();
        }
    }

    private void onDrawProgress() {
        if (mValueAnimatorRect != null) {
            mValueAnimatorRect.cancel();
        }
        if (mValueAnimatorProgress != null) {
            mValueAnimatorProgress.start();
        } else {
            mValueAnimatorProgress = ValueAnimator.ofFloat(sMaxMultiple,sMultiple);
            mValueAnimatorProgress.setDuration(500);
            mValueAnimatorProgress.addUpdateListener(animation -> {
                float rate = (float) animation.getAnimatedValue();
                mMultiple = rate;
                invalidate();
                if (rate == sMultiple) {
                    status = DOWNLOADING;
                    invalidate();
                }
            });
            mValueAnimatorProgress.start();
        }
    }

    public void onStartProgress() {
        onDrawProgress();
    }

    public void onPauseProgress() {
        status = PAUSE;
        onDrawRect();
    }

    public void onFinishProgress() {
        mSweepAngle = 360;
        status = SUCCESS;
        onDrawRect();
        if (listener != null) {
            listener.onProgressFinish();
            mSweepAngle = 0;
        }
    }

    /**
     * 设置进度条进度
     * @param curProgress
     * @param maxProgress
     */
    public void setProgress(int curProgress,int maxProgress) {
        if (curProgress < maxProgress) {
            BigDecimal decimal = new BigDecimal(curProgress).divide(new BigDecimal(maxProgress),2,BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(360));
            mSweepAngle = decimal.intValue();
            L.e("progress%d,sweepAngle%d",curProgress,mSweepAngle);
        } else {
            mSweepAngle = 360;
        }
        invalidate();
    }

    public void setListener(DownloadProgressListener listener) {
        this.listener = listener;
    }

    private int getLengthSpec(int measureSpec, boolean isWidth) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        int length = 0;
        if (mode == MeasureSpec.AT_MOST) {
            if (isWidth) {
                length = (int) ScreenUtils.dp2px(getContext(), 100);
            } else {
                length = (int) ScreenUtils.dp2px(getContext(),50);
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

    @IntDef({NORMAL,DOWNLOADING,SUCCESS,FAILURE,PAUSE})
    public @interface Status {
    }
}
