package com.magicalrice.game.snake;

import android.graphics.Color;

/**
 * Created by Adolph on 2018/2/12.
 */

public class GridSquare {
    private int mType;  //元素类型

    public GridSquare(int type) {
        this.mType = type;
    }

    public int getColor() {
        switch (mType) {
            case GameType.GRID:
                return Color.WHITE;
            case GameType.FOOD:
                return Color.BLUE;
            case GameType.SNAKE:
                return Color.parseColor("#FF4081");
        }
        return Color.WHITE;
    }

    public void setType(int type) {
        this.mType = type;
    }
}
