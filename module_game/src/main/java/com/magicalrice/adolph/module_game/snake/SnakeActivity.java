package com.magicalrice.adolph.module_game.snake;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.magicalrice.adolph.common.base.BaseActivity;
import com.magicalrice.adolph.common.base.RouterTable;
import com.magicalrice.adolph.module_game.R;

/**
 * Created by Adolph on 2018/2/12.
 */
@Route(path = RouterTable.ITEM_GAME_SNAKE,name = "贪吃蛇")
public class SnakeActivity extends BaseActivity implements View.OnClickListener {

    private SnakePanelView snakeView;

    @Override
    protected int getContentViewId() {
        return R.layout.g_activity_snack;
    }

    @Override
    protected void initUI() {
        snakeView = (SnakePanelView) findViewById(R.id.snake_view);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        findViewById(R.id.btn_top).setOnClickListener(SnakeActivity.this);
        findViewById(R.id.btn_left).setOnClickListener(SnakeActivity.this);
        findViewById(R.id.btn_right).setOnClickListener(SnakeActivity.this);
        findViewById(R.id.btn_bottom).setOnClickListener(SnakeActivity.this);
        findViewById(R.id.btn_start).setOnClickListener(SnakeActivity.this);
    }

    @Override
    protected String getDemoName() {
        return null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_top) {
            snakeView.setSnakeDirection(GameType.TOP);
        } else if (view.getId() == R.id.btn_left) {
            snakeView.setSnakeDirection(GameType.LEFT);
        } else if (view.getId() == R.id.btn_right) {
            snakeView.setSnakeDirection(GameType.RIGHT);
        } else if (view.getId() == R.id.btn_bottom) {
            snakeView.setSnakeDirection(GameType.BOTTOM);
        } else if (view.getId() == R.id.btn_start) {
            snakeView.restartGame();
        }
    }
}
