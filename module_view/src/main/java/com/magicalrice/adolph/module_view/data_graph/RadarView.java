package com.magicalrice.adolph.module_view.data_graph;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.magicalrice.adolph.common.utils.L;

import java.util.List;

/**
 * Created by Adolph on 2018/6/19.
 */

public class RadarView extends View {
    private Paint bgPaint;
    private Paint valuePaintOne;
    private Paint valuePaintTwo;
    private float radiusStorage;
    private float radius;
    private int centerX;
    private int centerY;
    private float angle;
    private int count = 4;
    private List<Double> data;
    private List<Double> data1;
    private float maxValue = 100;
    private int alpha = 128;

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //设置虚线的间隔和点的长度
        PathEffect effects = new DashPathEffect(new float[]{20f, 10f}, 0);
        //背景画笔
        bgPaint = new Paint();
        bgPaint.setPathEffect(effects);
        bgPaint.setColor(Color.GRAY);
        bgPaint.setAntiAlias(true);
        bgPaint.setStrokeWidth(1);
        bgPaint.setStyle(Paint.Style.STROKE);
        //第一个图层画笔
        valuePaintOne = new Paint();
        valuePaintOne.setColor(Color.RED);
        valuePaintOne.setAntiAlias(true);
        valuePaintOne.setStyle(Paint.Style.FILL);
        //第二个图层画笔
        valuePaintTwo = new Paint();
        valuePaintTwo.setColor(Color.BLUE);
        valuePaintTwo.setAntiAlias(true);
        valuePaintTwo.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radiusStorage = radius = Math.min(w, h) / 2 * 1f;
        centerX = w / 2;
        centerY = h / 2;
        startAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPolygon(canvas);
        drawRegion(valuePaintOne, data, canvas);
        drawRegion(valuePaintTwo, data1, canvas);
    }

    /**
     * 绘制背景多边形
     */
    private void drawPolygon(Canvas canvas) {
        Path path = new Path();

        angle = (float) (Math.toRadians(360) / count);
        path.reset();
        for (int j = 0; j < count; j++) {
            if (j == 0) {
                path.moveTo(centerX + radiusStorage, centerY);
            } else {
                /**
                 * 直角三角形:
                 * sin(x)是对边比斜边
                 * cos(x)是底边比斜边
                 * tan(x)是对边比底边
                 * 推导出:底边(x坐标)=斜边(半径)*cos(夹角角度),对边(y坐标)=斜边(半径)*sin(夹角角度)
                 */
                float x = (float) (centerX + radiusStorage * Math.cos(angle * j));
                float y = (float) (centerY + radiusStorage * Math.sin(angle * j));
                path.lineTo(x, y);
            }
        }
        path.close();
        canvas.drawPath(path, bgPaint);

        //绘制虚线
        for (int i = 0; i < count * 2; i++) {
            path.reset();
            path.moveTo(centerX, centerY);
            float x = (float) (centerX + (radiusStorage + 18) * Math.cos(angle / 2 * i));
            float y = (float) (centerY + (radiusStorage + 18) * Math.sin(angle / 2 * i));
            path.lineTo(x, y);
            canvas.drawPath(path, bgPaint);
        }
    }

    /**
     * 绘制覆盖区域
     */
    private void drawRegion(Paint paint, List<Double> data, Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < count; i++) {
            Double perCenter = data.get(i) / maxValue;
            double perRadius = perCenter * radius;
            float x = (float) (centerX + perRadius * Math.cos(angle * i));
            float y = (float) (centerY + perRadius * Math.sin(angle * i));
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        //闭合覆盖区域
        path.close();
        //填充覆盖区域
        paint.setAlpha(alpha);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);
    }

    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, radiusStorage);
        animator.setDuration(400);
        animator.addUpdateListener(animation -> {
            radius = (float) animation.getAnimatedValue();
            alpha = (int) (radius / radiusStorage * 128);
            invalidate();
        });
        animator.start();
    }

    //设置数值
    public void setData(List<Double> data, List<Double> data1) {
        this.data = data;
        this.data1 = data1;
    }
}
