package com.magicalrice.adolph.module_game.scyscraper.gameview;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Handler;

import com.magicalrice.adolph.module_game.scyscraper.ScyScraperActivity;

import static com.magicalrice.adolph.module_game.scyscraper.base.Constant.BASE_HEIGHT;
import static com.magicalrice.adolph.module_game.scyscraper.base.Constant.LINE_OFF_BOX;
import static com.magicalrice.adolph.module_game.scyscraper.base.Constant.UNIT_SIZE;

/**
 * Created by Adolph on 2018/1/21.
 */

public class MySurfaceView extends GLSurfaceView {

    public ScyScraperActivity mActivity;
    private int objectCount = 0;            //得分
    private int failCount = 0;              //失败次数
    private boolean isWin = false;          //是否胜利标志
    private boolean isFail = false;         //是否失败标志
    private boolean beginFlag = false;      //动画线程开始的标志，true为开始
    private float targetPointY = (BASE_HEIGHT + LINE_OFF_BOX) * UNIT_SIZE;       //箱子坐标，目标点y坐标
    private float cx = -8f;                 //摄像机x坐标
    private float cy = targetPointY + 1f;   //摄像机y坐标
    private float cz = 18;                  //摄像机z坐标
    private float tx = 0;                   //观察目标点x坐标
    private float ty = targetPointY;        //观察目标点y坐标
    private float tz = 0;                   //观察目标点z坐标

    private float mWidth,mHeight,mRatio;    //屏幕宽度、高度、宽高比
    private Handler handler;




    public MySurfaceView(Context context) {
        super(context);
    }
}
