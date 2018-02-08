package com.magicalrice.adolph.common.base;

import android.app.Application;

/**
 * Created by Adolph on 2018/1/30.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        initLogger();
//        initX5WebKit();

    }

//    private void initLogger() {
//        FormatStrategy strategy = PrettyFormatStrategy.newBuilder()
//                .showThreadInfo(false)
//                .methodCount(4)
//                .methodOffset(6)
//                .tag("Android Test:")
//                .build();
//        Logger.addLogAdapter(new AndroidLogAdapter(strategy) {
//            @Override
//            public boolean isLoggable(int priority, String tag) {
//                return true;
//            }
//        });
//    }
//
//    private void initX5WebKit() {
//        QbSdk.PreInitCallback callback = new QbSdk.PreInitCallback() {
//            @Override
//            public void onCoreInitFinished() {
//            }
//
//            @Override
//            public void onViewInitFinished(boolean b) {
//                Logger.e("App", "onViewInitFinished is " + b);
//            }
//        };
//
//        QbSdk.initX5Environment(getApplicationContext(), callback);
//    }
}
