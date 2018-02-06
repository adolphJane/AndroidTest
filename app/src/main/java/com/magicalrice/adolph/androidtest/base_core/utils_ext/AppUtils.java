package com.magicalrice.adolph.androidtest.base_core.utils_ext;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.magicalrice.adolph.androidtest.base_core.TestDemoApplication;
import com.magicalrice.adolph.androidtest.base_core.bean.AppInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adolph on 2018/2/5.
 */

public class AppUtils {
    private AppUtils() {
        throw new UnsupportedOperationException("AppUtils can`t be initialized.");
    }

    /**
     * Whether the app has bean installed
     *
     * @param action
     * @param category
     * @return
     */
    public static boolean isInstallApp(final String action, final String category) {
        Intent intent = new Intent(action);
        intent.addCategory(category);
        PackageManager manager = TestDemoApplication.getInstance().getPackageManager();
        ResolveInfo info = manager.resolveActivity(intent, 0);
        return info != null;
    }

//    /**
//     * Whether the app has bean installed
//     * @param packageName 包名
//     * @return
//     */
//    public static boolean isInstallApp(final String packageName) {
//
//    }

//    /**
//     * Whether the app has been installed
//     * @return
//     */
//    public static boolean hasAppRoot() {
//
//    }

    /**
     * 判断App是否是Debug版本
     *
     * @return
     */
    public static boolean isAppDebug() {
        return isAppDebug(ApplicationUtils.getApp().getPackageName());
    }

    /**
     * 判断App是否是Debug版本
     *
     * @param packName 包名
     * @return
     */
    public static boolean isAppDebug(final String packName) {
        if (isSpace(packName)) return false;
        try {
            PackageManager pm = ApplicationUtils.getApp().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取App签名
     *
     * @return
     */
    public static Signature[] getAppSignature() {
        return getAppSignature(ApplicationUtils.getApp().getPackageName());
    }

    /**
     * 获取App签名
     *
     * @param packageName
     * @return
     */
    public static Signature[] getAppSignature(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = ApplicationUtils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return pi == null ? null : pi.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取应用签名的SHA1值
     *
     * @return
     */
    public static String getAppSignatureSHA1() {
        return getAppSignatureSHA1(ApplicationUtils.getApp().getPackageName());
    }

    /**
     * 获取应用签名的SHA1值
     *
     * @param packageName
     * @return
     */
    public static String getAppSignatureSHA1(final String packageName) {
        Signature[] signatures = getAppSignature(packageName);
        if (signatures == null) return null;
        return EncryptUtils.encryptSHA1ToString(signatures[0].toByteArray()).replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
    }

    /**
     * 判断App是否处于前台
     *
     * @return
     */
    public static boolean isAppForeground() {
        ActivityManager manager = (ActivityManager) ApplicationUtils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        if (infos == null || infos.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
                return info.processName.equals(ApplicationUtils.getApp().getPackageName());
        }
        return false;
    }

    /**
     * 判断该app是否是处于前台状态
     * <p>当不是当前App,且SDK大于21时，需添加权限{@code <uses-permission android:name="android.permision.PACKAGE_USAGE_STATS"/>}</>
     *
     * @param packageName
     * @return
     */
    public static boolean isAppForeground(final String packageName) {
        return !isSpace(packageName) && packageName.equals(ProcessUtils.getForegroundProcessName());
    }

    /**
     * 获取App信息
     *
     * @return
     */
    public static AppInfo getAppInfo() {
        return getAppInfo(TestDemoApplication.getInstance().getPackageName());
    }

    /**
     * 获取App信息
     *
     * @param packageName 包名
     * @return
     */
    public static AppInfo getAppInfo(final String packageName) {
        try {
            PackageManager pm = TestDemoApplication.getInstance().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return getBean(pm, pi);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取所有已安装的App信息
     *
     * @return
     */
    public static List<AppInfo> getAppsInfo() {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = TestDemoApplication.getInstance().getPackageManager();
        //获取系统中安装的应用信息
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo pi : installedPackages) {
            AppInfo appInfo = getBean(pm, pi);
            if (appInfo == null) continue;
            list.add(appInfo);
        }
        return list;
    }

    /**
     * 得到AppInfo的bean
     *
     * @param pm 包的管理
     * @param pi 包的信息
     * @return
     */
    private static AppInfo getBean(final PackageManager pm, final PackageInfo pi) {
        if (pm == null || pi == null) return null;
        ApplicationInfo ai = pi.applicationInfo;
        String packageName = pi.packageName;
        String name = ai.loadLabel(pm).toString();
        Drawable icon = ai.loadIcon(pm);
        String packagePath = ai.sourceDir;
        String versionName = pi.versionName;
        int versionCode = pi.versionCode;
        boolean isSystem = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0;
        return new AppInfo(name, icon, packageName, packagePath, versionName, versionCode, isSystem);
    }

    /**
     * 清除App内所有数据
     *
     * @param dirPaths 路径
     * @return
     */
    public static boolean cleanAppData(final String... dirPaths) {
        File[] dirs = new File[dirPaths.length];
        int i = 0;
        for (String strPth : dirPaths) {
            dirs[i++] = new File(strPth);
        }
        return cleanAppData(dirs);
    }

    /**
     * 清除App内所有数据
     *
     * @param dirs 目录
     * @return
     */
    public static boolean cleanAppData(final File... dirs) {
        boolean isSuccess = CleanUtils.cleanInternalCache();
        isSuccess &= CleanUtils.cleanInternalDbs();
        isSuccess &= CleanUtils.cleanExternalCache();
        isSuccess &= CleanUtils.cleanInternalSP();
        isSuccess &= CleanUtils.cleanInternalFiles();
        for (File file : dirs) {
            isSuccess &= CleanUtils.cleanCustomCache(file);
        }
        return isSuccess;
    }

    /**
     * 判断路径是否正常
     *
     * @param s 路径
     * @return
     */
    private static boolean isSpace(String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
