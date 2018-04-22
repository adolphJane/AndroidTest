package com.magicalrice.adolph.module_game.scyscraper.base;

import javax.microedition.khronos.opengles.GL10;

import static com.magicalrice.adolph.module_game.scyscraper.base.Constant.UNIT_SIZE;

public class SingleBox {
    public float x,y,z;            //箱子的中心点x,y,z坐标

    public float mAngleX,mAngleY,mAngleZ;       //箱子沿x,y,z轴旋转角度
    private BoxGroup bg;                        //箱子管理组

    public SingleBox(BoxGroup group) {
        this.bg = group;
    }

    public void drawSelf(GL10 gl) {
        gl.glPushMatrix();          //保存当前矩阵

        bg.box.mOffsetX = x * UNIT_SIZE;        //箱子沿x轴平移距离
        bg.box.mOffsetY = y * UNIT_SIZE;        //箱子沿y轴平移距离
        bg.box.mOffsetZ = z * UNIT_SIZE;        //箱子沿z轴平移距离

        bg.box.mAngleX = mAngleX;               //箱子沿x轴旋转角度
        bg.box.mAngleY = mAngleY;               //箱子沿y轴旋转角度
        bg.box.mAngleZ = mAngleZ;               //箱子沿z轴旋转角度

        bg.box.drawSelf(gl);
        gl.glPopMatrix();           //恢复当前矩阵
        bg.box.mAngleZ = 0;         //将箱子沿z轴旋转角度
    }
}
