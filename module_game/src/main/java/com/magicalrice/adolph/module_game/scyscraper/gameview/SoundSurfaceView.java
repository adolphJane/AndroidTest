package com.magicalrice.adolph.module_game.scyscraper.gameview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.magicalrice.adolph.module_game.R;
import com.magicalrice.adolph.module_game.scyscraper.ScyScraperActivity;

/**
 * Created by Adolph on 2017/12/25.
 */

public class SoundSurfaceView extends SurfaceView implements SurfaceHolder.Callback2 {

    private ScyScraperActivity mActivity;

    private Paint mPaint;
    private int mScreenWidth = 320;   //屏幕宽度
    private int mScreenHeight = 480;  //屏幕高度
    private int mHintWidth = 100;   //提示宽度
    private int mHintHeight = 20;   //提示高度

    private Bitmap mSoundYN;    //是否打开声音提示的图片
    private Bitmap mSoundYES;   //打开声音图片
    private Bitmap mSoundNo;    //关闭声音图片
    private Bitmap mBackground; //背景图片

    public SoundSurfaceView(ScyScraperActivity context) {
        super(context);
        this.mActivity = context;
        initBitmap();
        this.getHolder().addCallback(this);
    }

    //初始化图片
    private void initBitmap(){
        mSoundYN = BitmapFactory.decodeResource(getResources(), R.drawable.sound_tv1);
        mSoundYES = BitmapFactory.decodeResource(getResources(),R.drawable.sound_tv_yes);
        mSoundNo = BitmapFactory.decodeResource(getResources(),R.drawable.sound_tv_no);
        mBackground = BitmapFactory.decodeResource(getResources(),R.drawable.sound_bg);
    }

    protected void onDraws(Canvas canvas) {
        canvas.drawBitmap(mBackground,0,0,null);
        canvas.drawBitmap(mSoundYN,mScreenWidth / 2 - mHintWidth / 2,mScreenHeight / 2 - mHintHeight / 2, null);
        canvas.drawBitmap(mSoundYES,mScreenWidth - 32,mScreenHeight - 16,null);
        canvas.drawBitmap(mSoundNo,0,mScreenHeight - 16,null);
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = null;//初始化画布
        try {
            canvas = holder.lockCanvas();//画布加锁
            synchronized (holder){
                onDraws(canvas);//重绘
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (canvas != null){
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
        int x = (int) event.getX();//获得屏幕被触控点x坐标
        int y = (int) event.getY();//获得屏幕被触控点y坐标

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //打开声音
                if (x < mScreenWidth && x > mScreenWidth - 32 && y < mScreenHeight && y > mScreenHeight - 16){
                }
                //关闭声音
                if (x < 32 && x > 0 && y < mScreenHeight && y > mScreenHeight - 16){

                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return false;
    }
}
