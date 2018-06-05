package com.magicalrice.adolph.base_utils.utils;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;

import com.magicalrice.adolph.base_utils.app_utils.AppManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Adolph on 2018/2/5.
 * desc: 进程相关工具类
 */

public class ProcessUtils {
    private ProcessUtils() {
        throw new UnsupportedOperationException("ProcessUtils can`t be initialized");
    }

    /**
     * 获取前台线程包名
     * <p>当不是查看当前App,且SDK大于21时，需添加权限{@code <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS"/>}</p>
     *
     * @return
     */
    public static String getForegroundProcessName() {
        ActivityManager manager = (ActivityManager) AppManager.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        if (infos != null && infos.size() > 0) {
            for (ActivityManager.RunningAppProcessInfo info : infos) {
                if (info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return info.processName;
                }
            }
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            PackageManager pm = AppManager.getApp().getPackageManager();
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (list.size() > 0) {
                try {
                    ApplicationInfo info = pm.getApplicationInfo(AppManager.getApp().getPackageName(), 0);
                    AppOpsManager aom = (AppOpsManager) AppManager.getApp().getSystemService(Context.APP_OPS_SERVICE);
                    if (aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, info.uid, info.packageName) != AppOpsManager.MODE_ALLOWED) {
                        AppManager.getApp().startActivity(intent);
                    }
                    if (aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, info.uid, info.packageName) != AppOpsManager.MODE_ALLOWED) {
                        Log.e("getForegroundApp", "没有打开\"有权查看使用权限的应用\"选项");
                        return null;
                    }
                    UsageStatsManager usageStatsManager = (UsageStatsManager) AppManager.getApp().getSystemService(Context.USAGE_STATS_SERVICE);
                    long endTime = System.currentTimeMillis();
                    long beginTime = endTime - 86400000 * 7;
                    List<UsageStats> usageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, beginTime, endTime);
                    if (usageStats == null || usageStats.isEmpty()) return null;
                    UsageStats recentStats = null;
                    for (UsageStats stats : usageStats) {
                        if (recentStats == null || stats.getLastTimeUsed() > recentStats.getLastTimeUsed())
                            recentStats = stats;
                    }
                    return recentStats == null ? null : recentStats.getPackageName();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("ProcessUtils", "getForegroundProcessName() called" + ": 无\"有权查看使用权限的应用选项\"");
            }
        }
        return null;
    }

    /**
     * 获取后台服务进程
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES"/>}</p>
     *
     * @return
     */
    public static Set<String> getAllBackgroundProcess() {
        ActivityManager manager = (ActivityManager) AppManager.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        Set<String> set = new HashSet<>();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            Collections.addAll(set, info.pkgList);
        }
        return set;
    }

    /**
     * 杀死所有的后台服务进程
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES"/>}</p>
     *
     * @return
     */
    public static Set<String> killAllBackgroundProcess() {
        ActivityManager manager = (ActivityManager) AppManager.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        Set<String> set = new HashSet<>();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            for (String pkg : info.pkgList) {
                manager.killBackgroundProcesses(pkg);
                set.add(pkg);
            }
        }
        infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            for (String pkg : info.pkgList) {
                set.remove(pkg);
            }
        }
        return set;
    }

    /**
     * 杀死后台服务进程
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES"/>}</p>
     *
     * @param packageName
     * @return
     */
    public static boolean killBackgroundProcess(@NonNull final String packageName) {
        ActivityManager manager = (ActivityManager) AppManager.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        if (infos == null || infos.size() == 0) return true;
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (Arrays.asList(info.pkgList).contains(packageName)) {
                manager.killBackgroundProcesses(packageName);
            }
        }
        infos = manager.getRunningAppProcesses();
        if (infos == null || infos.size() == 0) return true;
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (Arrays.asList(info.pkgList).contains(packageName)) {
                return false;
            }
        }
        return true;
    }
}
