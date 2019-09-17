package com.magicalrice.view.cloud_tag;

/**
 * Created by Adolph on 2018/4/23.
 */

public class TagBean {
    private int mPositionX,mPositionY;
    private String mContent;
    private boolean isCircle;
    private int bgColor,contentColor;
    private float floatDistance;

    public int getmPositionX() {
        return mPositionX;
    }

    public void setmPositionX(int mPositionX) {
        this.mPositionX = mPositionX;
    }

    public int getmPositionY() {
        return mPositionY;
    }

    public void setmPositionY(int mPositionY) {
        this.mPositionY = mPositionY;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public boolean isCircle() {
        return isCircle;
    }

    public void setCircle(boolean circle) {
        isCircle = circle;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public int getContentColor() {
        return contentColor;
    }

    public void setContentColor(int contentColor) {
        this.contentColor = contentColor;
    }

    public float getFloatDistance() {
        return floatDistance;
    }

    public void setFloatDistance(float floatDistance) {
        this.floatDistance = floatDistance;
    }
}
