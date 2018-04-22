package com.magicalrice.adolph.module_game.scyscraper.base;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class BoxGroup {
    Box box;               //箱子
    private ArrayList<SingleBox> al = new ArrayList<>();
    private SingleBox mSingleBox;   //单个箱子

    public BoxGroup(int drawableId) {
        box = new Box(drawableId);                 //初始化箱子
        mSingleBox = new SingleBox(this);
        al.add(mSingleBox);                         //将单个箱子加入到列表中
    }

    public void drawSelf(GL10 gl) {
        for (int i = 0;i < al.size();i++) {
            al.get(i).drawSelf(gl);                 //绘制箱子
        }
    }
}
