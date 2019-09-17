package com.magicalrice.common.base;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.magicalrice.base_libs.app_utils.AppManager;
import com.magicalrice.common.BuildConfig;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.CrashReport.UserStrategy;

import leakcanary.LeakCanary;

/**
 * Created by Adolph on 2018/1/30.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppManager.getInstance().init(this);
        initLogger();
        initBugly();
    }

    private void initLogger() {

    }

    private void initBugly() {
        UserStrategy strategy = new UserStrategy(this);
        strategy.setAppChannel("myChannel");
        strategy.setAppVersion(BuildConfig.VERSION_NAME);
        CrashReport.initCrashReport(getApplicationContext(), "5d8177ce84", false);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
