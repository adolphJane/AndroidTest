package com.magicalrice.adolph.androidtest.base_core;

import android.app.Application;

import com.magicalrice.adolph.androidtest.BuildConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by Adolph on 2018/1/30.
 */

public class TestDemoApplication extends Application {

    private static TestDemoApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        initLogger();
        initX5WebKit();

    }

    private void initLogger() {
        FormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(4)
                .methodOffset(6)
                .tag("Android Test:")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(strategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    private void initX5WebKit() {
        QbSdk.PreInitCallback callback = new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
            }

            @Override
            public void onViewInitFinished(boolean b) {
                Logger.e("App", "onViewInitFinished is " + b);
            }
        };

        QbSdk.initX5Environment(getApplicationContext(), callback);
    }

    public static TestDemoApplication getInstance() {
        if (instance == null) {
            instance = new TestDemoApplication();
        }
        return instance;
    }
}
