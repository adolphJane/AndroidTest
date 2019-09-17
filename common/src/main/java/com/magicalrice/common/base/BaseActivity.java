package com.magicalrice.common.base;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.magicalrice.common.R;

/**
 * Created by Adolph on 2018/1/30.
 */

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    //the time of the last click
    private long mLastClickTime;
    //the interval between twice click
    public static final int CLICK_TIME = 500;
    protected Toolbar toolbar;
    private Fragment currentFragment;
    protected T binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getContentViewId());
        //initialize the ui
        initUI();
        //initialize the data
        initData();
        //initialize the listener
        initListener();
    }

    protected abstract int getContentViewId();

    protected void initToolbar() {
        try {
            toolbar = findViewById(R.id.tool_bar);
            toolbar.setTitleTextColor(Color.WHITE);
            setSupportActionBar(toolbar);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
