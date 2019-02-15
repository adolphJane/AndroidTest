package com.magicalrice.adolph.module_view.testview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.magicalrice.adolph.base_utils.LogUtils;
import com.magicalrice.adolph.module_view.R;

import androidx.annotation.Nullable;

/**
 * Created by Adolph on 2018/3/28.
 */

public class TestView extends View {
    private Paint paint;
    private Bitmap bitmap;
    private int i = 0;
    private float scale;
    private boolean isStart = false;

    public TestView(Context context) {
        this(context,null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setAlpha(255);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_yurenjie);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isStart) {
            LogUtils.e(i+"test");
            canvas.save();
            Matrix matrix = new Matrix();
            scale = (getResources().getDisplayMetrics().widthPixels * 0.5f) / (bitmap.getWidth() + 0.00f);
            matrix.postScale(scale, scale);
            matrix.postTranslate(getResources().getDisplayMetrics().widthPixels * 0.2f - (bitmap.getWidth() * scale) / 2, getResources().getDisplayMetrics().heightPixels * 0.2f - (bitmap.getHeight() * scale) / 2);
            if (i >= 0 && i < 4) {
                matrix.postTranslate(0,-400 + (i + 1) * 100);
            } else if (i >= 4 && i < 7) {
                matrix.postScale(1,1 + (i - 3) * 0.1f,bitmap.getWidth() * scale / 2,bitmap.getHeight() * scale * 1.2f);
            } else if (i >= 7 && i < 10) {
                matrix.postScale(1,1.3f - (i - 6) * 0.1f,bitmap.getWidth() * scale / 2,bitmap.getHeight() * scale * 1.2f);
            } else {
                isStart = false;
                i = 0;
            }

            if (i >= 0 && i < 10) {
                canvas.drawBitmap(bitmap,matrix,paint);
            }
            canvas.restore();


            handler.sendEmptyMessageDelayed(1001,100);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1001) {
                i++;
                invalidate();
            }
        }
    };

    public void start() {
        isStart = true;
        invalidate();
    }
}
