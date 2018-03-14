package com.magicalrice.adolph.common.base;

import android.app.Application;

import com.letv.sarrsdesktop.blockcanaryex.jrt.BlockCanaryEx;
import com.letv.sarrsdesktop.blockcanaryex.jrt.Config;
import com.letv.sarrsdesktop.blockcanaryex.jrt.FrequentMethodInfo;
import com.letv.sarrsdesktop.blockcanaryex.jrt.MethodInfo;
import com.magicalrice.adolph.common.BuildConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;
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
        initBlockcCanary();
        initBugly();
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

    private void initBlockcCanary() {
        if(!BlockCanaryEx.isInSamplerProcess(this)) {
            BlockCanaryEx.install(new Config(this) {
                /**
                 * If need notification to notice block.
                 * @return true if need, else if not need.
                 */
                @Override
                public boolean displayNotification() {
                    return true;
                }

                /**
                 * judge whether the loop is blocked, you can override this to decide
                 * whether it is blocked by your logic
                 * @param costRealTimeMs
                 * @param costThreadTimeMs
                 * @param creatingActivity
                 * @param isApplicationCreating
                 * @param inflateCostTimeMs
                 * @return
                 */
                @Override
                public boolean isBlock(long costRealTimeMs, long costThreadTimeMs, String creatingActivity, boolean isApplicationCreating, long inflateCostTimeMs) {
                    if (creatingActivity != null || isApplicationCreating) {
                        return costRealTimeMs > 250L;
                    } else {
                        return costRealTimeMs > 100L && costThreadTimeMs > 8L;
                    }
                }

                /**
                 * judge whether the method is heavy method, we will print heavy method in log
                 * Note: running in none ui thread
                 * @param methodInfo
                 * @return
                 */
                @Override
                public boolean isHeavyMethod(MethodInfo methodInfo) {
                    return (methodInfo.getCostThreadTime() > 0L && methodInfo.getCostRealTimeMs() > 0L || methodInfo.getCostRealTimeMs() > 2L);
                }

                /**
                 * judge whether the method is called frequently, we will print frequent method in log
                 * Note: running in none ui thread
                 * @param frequentMethodInfo
                 * @return
                 */
                @Override
                public boolean isFrequentMethod(FrequentMethodInfo frequentMethodInfo) {
                    return frequentMethodInfo.getTotalCostRealTimeMs() > 1L && frequentMethodInfo.getCalledTimes() > 1;
                }

                /**
                 * we will save block log to sdcard by default, if you want to disable this, just return false
                 * Warning: if save log disabled, new BlockInfo will not be displayed in DisplayActivity
                 * Note: running in none ui thread
                 * @return
                 */
                @Override
                public boolean enableSaveLog() {
                    return true;
                }

                /**
                 *  Path to save log, like "/blockcanary/", will save to sdcard if can. if we can't save log to sdcard (eg: no permission),
                 * else we will try to save to "${context.getExternalFilesDir("BlockCanaryEx")}${provideLogPath()}", if we failed too,
                 * we will save to "${context.getFilesDir()${provideLogPath()}"}"
                 *
                 * Note: running in none ui thread
                 * @return
                 */
                @Override
                public String provideLogPath() {
                    return super.provideLogPath();
                }

                /**
                 * Network type to record in log, you should impl this if you want to record this
                 * @return
                 */
                @Override
                public String provideNetworkType() {
                    return super.provideNetworkType();
                }

                /**
                 * unique id to record in log, you should impl this if you want to record this
                 * @return
                 */
                @Override
                public String provideUid() {
                    return super.provideUid();
                }
            });
        }
    }

    private void initBugly() {
        UserStrategy strategy = new UserStrategy(this);
        strategy.setAppChannel("myChannel");
        strategy.setAppVersion(BuildConfig.VERSION_NAME);
        strategy.setAppPackageName(BuildConfig.APPLICATION_ID);
        CrashReport.initCrashReport(getApplicationContext(), "5d8177ce84", false);
    }
}
