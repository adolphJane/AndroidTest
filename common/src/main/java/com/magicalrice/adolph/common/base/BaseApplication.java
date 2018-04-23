package com.magicalrice.adolph.common.base;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.magicalrice.adolph.common.BuildConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;
import com.sw.debug.view.DebugViewWrapper;
import com.sw.debug.view.modules.TimerModule;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.CrashReport.UserStrategy;

/**
 * Created by Adolph on 2018/1/30.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initLogger();
        initLeakCanary();
        initBugly();
        initDebugView();
    }

    private void initLogger() {
        FormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(5)
                .methodOffset(6)
                .tag("AndroidTest:")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(strategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return true;
            }
        });
    }

    private void initLeakCanary() {
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }
    }

    private void initBugly() {
        UserStrategy strategy = new UserStrategy(this);
        strategy.setAppChannel("myChannel");
        strategy.setAppVersion(BuildConfig.VERSION_NAME);
        strategy.setAppPackageName(BuildConfig.APPLICATION_ID);
        CrashReport.initCrashReport(getApplicationContext(), "5d8177ce84", false);
    }

    private void initDebugView() {
        DebugViewWrapper.Companion.getInstance().init(
                new DebugViewWrapper.Builder(this)
                    .viewWidth(250)
                    .bgColor(0x6f677700)
                    .alwaysShowOverlaySetting(true)
                    .logMaxLines(20)
        );

        DebugViewWrapper.Companion.getInstance().show();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        TimerModule.Companion.getInstance().begin(base);
    }
}
