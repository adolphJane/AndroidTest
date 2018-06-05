package com.magicalrice.adolph.common.utils;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.sw.debug.view.modules.LogModule;

/**
 * Created by Adolph on 2018/4/9.
 */

public class L {

    public static void e(String tag,String log, Object... args) {
        String newLog = formatMessage(log,args);

        if (!TextUtils.isEmpty(newLog)) {
            LogModule.Companion.getInstance().log(newLog);
            Logger.t(tag).e(newLog);
        }
    }

    public static void e(String log, Object... args) {
        String newLog = formatMessage(log,args);

        if (!TextUtils.isEmpty(newLog)) {
            LogModule.Companion.getInstance().log(newLog);
            Logger.e(newLog);
        }
    }

    public static void d(String log, Object... args) {
        String newLog = formatMessage(log,args);

        if (!TextUtils.isEmpty(newLog)) {
            LogModule.Companion.getInstance().log(newLog);
            Logger.d(newLog);
        }
    }

    public static void d(String tag,String log, Object... args) {
        String newLog = formatMessage(log,args);

        if (!TextUtils.isEmpty(newLog)) {
            LogModule.Companion.getInstance().log(newLog);
            Logger.t(tag).d(newLog);
        }
    }

    public static void i(String log, Object... args) {
        String newLog = formatMessage(log,args);

        if (!TextUtils.isEmpty(newLog)) {
            LogModule.Companion.getInstance().log(newLog);
            Logger.i(newLog);
        }
    }

    public static void i(String tag,String log, Object... args) {
        String newLog = formatMessage(log,args);

        if (!TextUtils.isEmpty(newLog)) {
            LogModule.Companion.getInstance().log(newLog);
            Logger.t(tag).i(newLog);
        }
    }

    private static String formatMessage(String log,Object... args) {
        try {
            return String.format(log, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
