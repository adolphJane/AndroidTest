package com.magicalrice.adolph.module_game.scyscraper.gameview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.magicalrice.adolph.module_game.scyscraper.ScyScraperActivity;

/**
 * Created by Adolph on 2017/12/26.
 */

public class StartSurfaceView extends SurfaceView implements SurfaceHolder.Callback2 {

    private ScyScraperActivity mActivity;
    private int mScreenWidth = 320;//屏幕宽度
    private int mScreenHeight = 480;//屏幕高度
    private int mPicWidth = 128;//图片宽
    private int mPicHeight = 32;//图片高
    private int mBlankWidth = 0;//留白
    private int mBlankHeight = 220;//高度留白
    private int mUnitBlankHeight = 5;//图片之间高度留白

    final int KSMS_VIEW = 0;//快速模式
    final int OPTION_VIEW = 1;//设置
    final int ABOUT_VIEW = 2;//关于
    final int HELP_VIEW = 3;//帮助
    final int EXIT_VIEW = 4;//退出

    private Bitmap mBackground;//背景图片
    private Bitmap mKsms;//快速模式图片
    private Bitmap mGameOption;//设置图片
    private Bitmap mGameExit;//退出图片
    private Bitmap mGameAbout;//关于图片
    private Bitmap mGameHelp;//帮助图片
    //未选中时按钮图片
    private Bitmap mKsmsShort;//快速模式
    private Bitmap mGameOptionShort;//设置
    private Bitmap mGameExitShort;//退出
    private Bitmap mGameAboutShort;//关于
    private Bitmap mGameHelpShort;//帮助
    //选中时按钮图片
    private Bitmap mKsmsLong;//快速模式
    private Bitmap mGameOptionLong;//设置
    private Bitmap mGameExitLong;//退出
    private Bitmap mGameAboutLong;//关于
    private Bitmap mGameHelpLong;//帮助

    private int status = KSMS_VIEW;

    private boolean mSoundFlag = true;//绘制声音提示的界面的标志
    private boolean mSoundMarker = false;//是否打开声音

    public StartSurfaceView(ScyScraperActivity context) {
        super(context);
        this.mActivity = context;
        initBitmap();

        this.getHolder().addCallback(this);

    }

    private void initBitmap() {
        mBackground = BitmapFactory.decodeResource(getResources(), R.drawable.start_bg);//初始化背景图片
        mKsmsShort = BitmapFactory.decodeResource(getResources(), R.drawable.ksms_short);//初始化快速模式图片
        mGameOptionShort = BitmapFactory.decodeResource(getResources(), R.drawable.option_short);//初始化设置图片
        mGameExitShort = BitmapFactory.decodeResource(getResources(), R.drawable.exit_short);//初始化退出图片
        mGameAboutShort = BitmapFactory.decodeResource(getResources(), R.drawable.about_short);//初始化关于图片
        mGameHelpShort = BitmapFactory.decodeResource(getResources(), R.drawable.help_short);//初始化帮助图片

        mKsmsLong = BitmapFactory.decodeResource(getResources(), R.drawable.ksms_long);
        mGameOptionLong = BitmapFactory.decodeResource(getResources(), R.drawable.option_long);
        mGameExitLong = BitmapFactory.decodeResource(getResources(), R.drawable.exit_long);
        mGameAboutLong = BitmapFactory.decodeResource(getResources(), R.drawable.about_long);
        mGameHelpLong = BitmapFactory.decodeResource(getResources(), R.drawable.help_long);
    }

    //改变按钮状态
    private void shiftButtonState(int index) {
        mKsms = mKsmsShort; //设置快速模式的图片按钮为按下状态
        mGameOption = mGameOptionShort; //设置设置的图片按钮为按下状态
        mGameAbout = mGameAboutShort; //设置关于的图片按钮为按下状态
        mGameHelp = mGameHelpShort; //设置帮助的图片按钮为按下状态
        mGameExit = mGameExitShort; //设置退出的图片按钮为按下状态

        switch (index) {
            case KSMS_VIEW:         //选中快速模式
                mKsms = mKsmsLong;
                break;
            case OPTION_VIEW:       //选中设置模式
                mGameOption = mGameOptionLong;
                break;
            case ABOUT_VIEW:        //选中关于模式
                mGameAbout = mGameAboutLong;
                break;
            case HELP_VIEW:         //选中帮助模式
                mGameHelp = mGameHelpLong;
                break;
            case EXIT_VIEW:         //选中退出模式
                mGameExit = mGameExitLong;
                break;
        }

        //重绘
        Canvas canvas = null;
        SurfaceHolder holder = this.getHolder();//定义并实例化SurfaceHolder对象

        try {
            canvas = holder.lockCanvas();//锁定画布
            synchronized (holder) {  //给画布加锁
                onDraws(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void onDraws(Canvas canvas) {
        canvas.drawBitmap(mBackground, 0, 0, null);        //绘制背景图片
        canvas.drawBitmap(mKsms, mBlankWidth, mBlankHeight, null); //绘制快速模式按钮
        canvas.drawBitmap(mGameOption, mBlankWidth, mBlankHeight + 1 * (mPicHeight + mUnitBlankHeight), null);//设置
        canvas.drawBitmap(mGameAbout, mBlankWidth, mBlankHeight + 2 * (mPicHeight + mUnitBlankHeight), null);//关于
        canvas.drawBitmap(mGameHelp, mBlankWidth, mBlankHeight + 3 * (mPicHeight + mUnitBlankHeight), null);//帮助
        canvas.drawBitmap(mGameExit, mBlankWidth, mBlankHeight + 4 * (mPicHeight + mUnitBlankHeight), null);//退出
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        shiftButtonState(status);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
                shiftButtonState(status);
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                status = (status + 1) % 5;  //status在0~5之间
                shiftButtonState(status);
                return true;
            case KeyEvent.KEYCODE_DPAD_UP:
                status = (status - 1 + 5) % 5;
                shiftButtonState(status);
                return true;
        }
        return false;
    }

    //切换界面
    public void shiftView(int index){
        switch (index){
            case KSMS_VIEW:
                break;
            case OPTION_VIEW:
                break;
            case ABOUT_VIEW:
                break;
            case HELP_VIEW:
                break;
            case EXIT_VIEW:
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();//获得屏幕x坐标
        int y = (int) event.getY();//获得屏幕y坐标

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (x > mBlankWidth && x < mBlankWidth + mPicWidth && y > mBlankHeight && y < mBlankHeight + mPicHeight){
                    //快速切换模式
                    status = KSMS_VIEW;
                    shiftButtonState(status);
                    shiftView(status);
                } else if (x > mBlankWidth && x < mBlankWidth + mPicWidth && y > mBlankHeight + 1 * (mPicHeight + mUnitBlankHeight) && y < mBlankHeight + 1 * (mPicHeight + mUnitBlankHeight) + mPicHeight){
                    //设置
                    status = OPTION_VIEW;
                    shiftButtonState(status);
                    shiftView(status);
                } else if (x > mBlankWidth && x < mBlankWidth + mPicWidth && y > mBlankHeight + 2 * (mPicHeight + mUnitBlankHeight) && y < mBlankHeight + 2 * (mPicHeight + mUnitBlankHeight) + mPicHeight){
                    //关于
                    status = ABOUT_VIEW;
                    shiftButtonState(status);
                    shiftView(status);
                } else if (x > mBlankWidth && x < mBlankWidth + mPicWidth && y > mBlankHeight + 3 * (mPicHeight + mUnitBlankHeight) && y < mBlankHeight + 3 * (mPicHeight + mUnitBlankHeight) + mPicHeight){
                    //帮助
                    status = HELP_VIEW;
                    shiftButtonState(status);
                    shiftView(status);
                } else if (x > mBlankWidth && x < mBlankWidth + mPicWidth && y > mBlankHeight + 4 * (mPicHeight + mUnitBlankHeight) && y < mBlankHeight + 4 * (mPicHeight + mUnitBlankHeight) + mPicHeight){
                    //退出
                    status = EXIT_VIEW;
                    System.exit(0);
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
