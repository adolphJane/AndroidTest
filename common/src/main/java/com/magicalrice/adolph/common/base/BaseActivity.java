package com.magicalrice.adolph.common.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.magicalrice.adolph.common.R;

/**
 * Created by Adolph on 2018/1/30.
 */

public abstract class BaseActivity extends AppCompatActivity {

    //the time of the last click
    private long mLastClickTime;
    //the interval between twice click
    public static final int CLICK_TIME = 500;

    protected Toolbar toolbar;

    private Fragment currentFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        setBase();
        AppManager.getInstance().addActivity(this);
        //initialize the ui
        initUI();
        //initialize the toolbar
        initToolbar();
        //initialize the data
        initData();
        //initialize the listener
        initListener();
    }

    protected abstract int getContentViewId();

    protected void initToolbar(){
        toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    protected abstract void initUI();

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract String getDemoName();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    protected void saveInstanceState(Bundle outState) {

    }

    protected void showLongToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }

    protected void showShortToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    //verify whether interval between twice click less than CLICK_TIME
    public boolean verifyClickTime() {
        if (System.currentTimeMillis() - mLastClickTime <= CLICK_TIME) {
            return false;
        }
        mLastClickTime = System.currentTimeMillis();
        return true;
    }

    public void closeInputMethod() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected String getStringMethod(int mStringRid) {
        return this.getResources().getString(mStringRid);
    }

    protected float getDemonIntegerMethod(int mDemonRid) {
        return this.getResources().getDimension(mDemonRid);
    }

    protected abstract void setBase();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().removeActivity(this);
    }

    public void fragmentReplace(int target, Fragment toFragment, boolean backStack) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        String toClassName = toFragment.getClass().getSimpleName();
        if (manager.findFragmentByTag(toClassName) == null) {
            transaction.replace(target, toFragment, toClassName);
            if (backStack) {
                transaction.addToBackStack(toClassName);
            }
            transaction.commit();
        }
    }

    public void smartFragmentReplace(int target, Fragment toFragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (currentFragment != null) {
            transaction.hide(toFragment);
        }
        String toClassName = toFragment.getClass().getSimpleName();
        if (manager.findFragmentByTag(toClassName) != null) {
            transaction.show(toFragment);
        } else {
            transaction.add(target, toFragment, toClassName);
        }
        transaction.commit();
        currentFragment = toFragment;
    }
}