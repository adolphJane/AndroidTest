package com.magicalrice.adolph.module_game.snake;

/**
 * Created by Adolph on 2018/2/12.
 */

public class GridPosition {
    private int x,y;

    public GridPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
