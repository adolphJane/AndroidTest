package com.magicalrice.cases.appbarscroll;

import androidx.annotation.IdRes;

/**
 * Created by Adolph on 2018/6/6.
 */

public interface OnAppBarListener {
    void onTitleBarItemClick(@IdRes int id);
    void initEnd(int miniTopHeight);
}
