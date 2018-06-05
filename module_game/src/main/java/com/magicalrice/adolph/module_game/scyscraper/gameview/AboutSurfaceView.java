package com.magicalrice.adolph.module_game.scyscraper.gameview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.magicalrice.adolph.module_game.R;
import com.magicalrice.adolph.module_game.scyscraper.ScyScraperActivity;

/**
 * Created by Adolph on 2018/1/21.
 */

public class AboutSurfaceView extends SurfaceView implements SurfaceHolder.Callback2 {

    private ScyScraperActivity mActivity;

    private int screenWidth = 320;          //屏幕宽度
    private int screenHeight = 480;         //屏幕高度
    private int picWidth = 112;             //返回按钮图片宽度
    private int picHeight = 40;             //返回按钮图片高度
    private Bitmap mBg;                //背景图片
    private Bitmap mGameBack;               //返回按钮图片

    public AboutSurfaceView(ScyScraperActivity activity) {
        super(activity);

        this.mActivity = activity;

        initBitmap();

        this.getHolder().addCallback(this);
    }

    private void initBitmap() {
        //加载背景图片
        mBg = BitmapFactory.decodeResource(getResources(), R.drawable.about_bg);
        //加载返回按钮图片
        mGameBack = BitmapFactory.decodeResource(getResources(),R.drawable.back);
    }

    public void drawB(Canvas canvas) {
        //画背景图片
        canvas.drawBitmap(mBg,0,0,null);
        //画返回按钮图片
        canvas.drawBitmap(mGameBack,(screenWidth - (1 * picWidth)),screenHeight - 2 * picHeight,null);
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {

    }

    //view创建的时候调用
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
                //画图完成之后给画布解锁
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    //view改变的时候调用
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    //view销魂的时候调用
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (x < (screenWidth - (1 * picWidth)) + picWidth && x > (screenWidth - (1 * picWidth)) && y < screenHeight - picHeight && y > screenHeight - 2 * picHeight) {
                    //切换到主菜单界面
                    mActivity.setMenuView();
                }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                mActivity.setMenuView();
                return true;
        }
        return false;
    }
}
