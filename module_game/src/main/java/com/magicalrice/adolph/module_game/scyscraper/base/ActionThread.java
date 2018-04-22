package com.magicalrice.adolph.module_game.scyscraper.base;

import com.magicalrice.adolph.module_game.scyscraper.gameview.MySurfaceView;

import static com.magicalrice.adolph.module_game.scyscraper.base.Constant.BASE_HEIGHT;
import static com.magicalrice.adolph.module_game.scyscraper.base.Constant.BASE_LENGTH;
import static com.magicalrice.adolph.module_game.scyscraper.base.Constant.BOX_SIZE;
import static com.magicalrice.adolph.module_game.scyscraper.base.Constant.LINE_LENGTH;
import static com.magicalrice.adolph.module_game.scyscraper.base.Constant.LINE_OFF_BOX;

public class ActionThread extends Thread {
    private MySurfaceView myView;           //主界面
    private BoxGroup boxGroup;              //箱子管理组
    private Line line;                      //绳子
    private SingleBox singleBox;            //绳子下面的箱子
    private SingleBox singleBox2;           //大楼上最上面的箱子
    private float lineHeight = BASE_HEIGHT + LINE_LENGTH + LINE_OFF_BOX;        //线上端点初始的高度
    private float downSpan = 0.01f;         //箱子每次下降的高度
    private boolean isRotateLeft = false;   //箱子是否左转
    private boolean isRotateRight = false;  //箱子是否右转
    private float rotateAngle = 0;          //箱子旋转角度
    private float angleSpan = downSpan / BOX_SIZE * 180;    //箱子每次的旋转角度
    private float xSpan = downSpan / BOX_SIZE * BASE_LENGTH / 2;    //箱子旋转时每次在x轴方向平移的距离
    public boolean beginDown = false;       //标志箱子是否已经开始下落

    public ActionThread(BoxGroup bg,Line line,MySurfaceView myView) {
        this.boxGroup = bg;
        this.line = line;
        this.myView = myView;
        line.mOffsetY = lineHeight;         //将线向上平移
    }

    @Override
    public void run() {
        super.run();
    }

    public void boxRotateLeft() {
        //当箱子y坐标小鱼最上面箱子y之后
        if (singleBox.y <= singleBox2.y) {
            isRotateLeft = false;          //停止向左旋转
            myView.mActivity.playSound(2,0);    //播放失败音乐
            return;
        }
        singleBox.x -= xSpan;               //箱子向左平移
        rotateAngle += angleSpan;           //箱子沿z轴旋转角度加上angleSpan
        singleBox.mAngleZ = rotateAngle;    //箱子沿z轴的旋转角度
    }

    public void boxRotateRight() {
        //当箱子y坐标小鱼最上面箱子y之后
        if (singleBox.y <= singleBox2.y) {
            isRotateRight = false;          //停止向右旋转
            myView.mActivity.playSound(2,0);    //播放失败音乐
            return;
        }
        singleBox.x += xSpan;               //箱子向右平移
        rotateAngle -= angleSpan;           //箱子沿z轴旋转角度减去angleSpan
        singleBox.mAngleZ = rotateAngle;    //箱子沿z轴的旋转角度
    }
}
