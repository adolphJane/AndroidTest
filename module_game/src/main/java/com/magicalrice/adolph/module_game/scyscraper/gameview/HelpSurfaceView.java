package com.magicalrice.adolph.module_game.scyscraper.gameview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.magicalrice.adolph.module_game.R;
import com.magicalrice.adolph.module_game.scyscraper.ScyScraperActivity;

public class HelpSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private ScyScraperActivity mActivity;
    private int screenWidth = 320;              //屏幕宽度
    private int screenHeight = 480;             //屏幕高度
    private int picWidth = 112;                 //返回按钮图片宽度
    private int picHeight = 40;                 //返回按钮图片高度
    private Bitmap bgAbout;                     //背景图片
    private Bitmap gameBack;                    //返回按钮图片

    public HelpSurfaceView(ScyScraperActivity context) {
        super(context);
        this.mActivity = context;
        initBitmap();
        this.getHolder().addCallback(this);
    }

    private void initBitmap() {
        bgAbout = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        gameBack = BitmapFactory.decodeResource(getResources(),R.drawable.back);
    }

    private void drawB(Canvas canvas) {
        canvas.drawBitmap(bgAbout,0,0,null);
        canvas.drawBitmap(gameBack,(screenWidth - (1 * picWidth)),screenHeight - 2 * picHeight,null);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = null;
        try {
            canvas = holder.lockCanvas();
            synchronized (holder) {
                drawB(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (x < (screenWidth - (1 * picWidth)) + picWidth && x > (screenWidth - (1 * picWidth)) && y < screenHeight - 2 * picHeight + picHeight && y > screenHeight - 2 * picHeight) {
            mActivity.setMenuView();
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                mActivity.setMenuView();            //切换到主菜单界面
                return true;
        }
        return false;                               //false,其他按键交给系统处理
    }
}
