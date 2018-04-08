package com.magicalrice.adolph.custom_view.option_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.magicalrice.adolph.common.utils.ScreenUtils;
import com.magicalrice.adolph.custom_view.R;
import com.orhanobut.logger.Logger;

/**
 * Created by Adolph on 2018/4/8.
 */
public class OptionLayout extends View {
    private Context mContext;
    private int optionStatus;
    private String optionContent;
    private Paint mPaint;
    private TextPaint mTextPaint;
    private int optionContentColor;
    private float optionContentSize;
    private int maxHeight, maxWidth;
    private int count;

    public OptionLayout(Context context) {
        this(context, null);
    }

    public OptionLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OptionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.cv_OptionLayout);
        optionStatus = a.getInteger(R.styleable.cv_OptionLayout_cvOptionStatus, 0);
        optionContent = a.getString(R.styleable.cv_OptionLayout_cvOptionContent);
        optionContentColor = a.getColor(R.styleable.cv_OptionLayout_cvOptionContentColor, 0);
        optionContentSize = a.getDimension(R.styleable.cv_OptionLayout_cvOptionContentSize, ScreenUtils.dp2px(mContext, 14));
        setWillNotDraw(false);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.colorAccent));

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(optionContentColor);
        mTextPaint.setTextSize(optionContentSize);

        float contentWidth = mTextPaint.measureText(optionContent);
        Paint.FontMetricsInt metrics = mTextPaint.getFontMetricsInt();
        if (contentWidth > ScreenUtils.dp2px(mContext, 250)) {
            Logger.e("count:%f,height:%d", contentWidth / ScreenUtils.dp2px(mContext, 250), metrics.bottom - metrics.top);
            if (contentWidth / ScreenUtils.dp2px(mContext, 250) > (int) (contentWidth / ScreenUtils.dp2px(mContext, 250))) {
                count = (int) (contentWidth / ScreenUtils.dp2px(mContext, 250)) + 1;
            } else {
                count = (int) (contentWidth / ScreenUtils.dp2px(mContext, 250));
            }
            maxHeight = (int) (count * (metrics.bottom - metrics.top) + ScreenUtils.dp2px(mContext, 10));
        } else {
            maxHeight = (int) (metrics.bottom - metrics.top + ScreenUtils.dp2px(mContext, 10));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getWidthMeasure(widthMeasureSpec), getHeightMeasure(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Logger.e("width:%d,height:%d", getMeasuredWidth(), getMeasuredHeight());

        RectF rectF = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
        if (optionStatus == 0) {
            mPaint.setColor(Color.WHITE);
        } else if (optionStatus == 1) {
            mPaint.setColor(Color.GREEN);
        } else if (optionStatus == 2) {
            mPaint.setColor(Color.RED);
        } else if (optionStatus == 3) {
            mPaint.setColor(Color.YELLOW);
        }
        canvas.drawRoundRect(rectF, ScreenUtils.dp2px(mContext, 20), ScreenUtils.dp2px(mContext, 20), mPaint);
        StaticLayout myTextLayout = new StaticLayout(optionContent, mTextPaint, (int) (getMeasuredWidth() - ScreenUtils.dp2px(mContext, 50)), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        canvas.save();
        Paint.FontMetricsInt metrics = mTextPaint.getFontMetricsInt();
        float baseLine = rectF.bottom - ((rectF.bottom - rectF.top - (metrics.bottom + metrics.top) * count) / 2 + metrics.bottom * count);
        canvas.translate(ScreenUtils.dp2px(mContext, 30), baseLine / 2);
        myTextLayout.draw(canvas);
        canvas.restore();
    }

    private int getWidthMeasure(int widthMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);

        int size = (int) ScreenUtils.dp2px(mContext, 200);
        if (mode == MeasureSpec.EXACTLY) {
            size = width;
        } else if (mode == MeasureSpec.AT_MOST) {
            size = (int) ScreenUtils.dp2px(mContext, 250);
        }
        return size;
    }

    private int getHeightMeasure(int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);

        int size = (int) ScreenUtils.dp2px(mContext, 40);
        if (mode == MeasureSpec.EXACTLY) {
            size = height;
        } else if (mode == MeasureSpec.AT_MOST) {
            size = maxHeight;
        }
        return size;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                changeStatus();
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    private void changeStatus() {
        if (optionStatus == 3) {
            optionStatus = 0;
        } else {
            optionStatus++;
        }
        invalidate();
    }
}
