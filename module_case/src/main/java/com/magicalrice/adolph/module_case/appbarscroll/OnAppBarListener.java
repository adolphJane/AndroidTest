package com.magicalrice.adolph.module_case.appbarscroll;

import android.support.annotation.IdRes;

/**
 * Created by Adolph on 2018/6/6.
 */

public interface OnAppBarListener {
    void onTitleBarItemClick(@IdRes int id);
    void initEnd(int miniTopHeight);
}
