package com.magicalrice.adolph.common.utils;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.sw.debug.view.modules.LogModule;

/**
 * Created by Adolph on 2018/4/9.
 */

public class L {
    public static void e(String log, Object... args) {
        String newLog = "";
        try {
            newLog = String.format(log, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(newLog)) {
            LogModule.Companion.getInstance().log(newLog);
            Logger.e(newLog);
        }
    }

    public static void d(String log, Object... args) {
        String newLog = "";
        try {
            newLog = String.format(log, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(newLog)) {
            LogModule.Companion.getInstance().log(newLog);
            Logger.d(newLog);
        }
    }

    public static void i(String log, Object... args) {
        String newLog = "";
        try {
            newLog = String.format(log, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(newLog)) {
            LogModule.Companion.getInstance().log(newLog);
            Logger.i(newLog);
        }
    }
}
