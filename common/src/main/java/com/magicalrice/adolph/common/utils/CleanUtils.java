package com.magicalrice.adolph.common.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Adolph on 2018/2/5.
 */

public final class CleanUtils {
    private CleanUtils() {
        throw new UnsupportedOperationException("CleanUtils can`t be initialized");
    }

    /**
     * 清楚内部缓存
     *
     * @return
     */
    public static boolean cleanInternalCache() {
        return deleteFilesInDir(ApplicationUtils.getApp().getCacheDir());
    }

    /**
     * 清除内部文件
     *
     * @return
     */
    public static boolean cleanInternalFiles() {
        return deleteFilesInDir(ApplicationUtils.getApp().getFilesDir());
    }

    /**
     * 清除内部数据库
     *
     * @return
     */
    public static boolean cleanInternalDbs() {
        return deleteFilesInDir(ApplicationUtils.getApp().getFilesDir().getParent() + File.separator + "databases");
    }

    /**
     * 根据名称清理数据库
     *
     * @param dbName
     * @return
     */
    public static boolean cleanInternalDbByName(final String dbName) {
        return ApplicationUtils.getApp().deleteDatabase(dbName);
    }

    /**
     * 清理内部SP
     *
     * @return
     */
    public static boolean cleanInternalSP() {
        return deleteFilesInDir(ApplicationUtils.getApp().getFilesDir().getParent() + File.separator + "shared_prefs");
    }

    /**
     * 清理外部缓存
     *
     * @return
     */
    public static boolean cleanExternalCache() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && deleteFilesInDir(ApplicationUtils.getApp().getExternalCacheDir());
    }

    /**
     * 清除指定目录下的文件
     *
     * @param dirPath 目录
     * @return
     */
    public static boolean cleanCustomCache(final String dirPath) {
        return deleteFilesInDir(dirPath);
    }

    /**
     * 清除指定目录下的文件
     *
     * @param dir 目录
     * @return
     */
    public static boolean cleanCustomCache(final File dir) {
        return deleteFilesInDir(dir);
    }

    public static boolean deleteFilesInDir(final String dirPath) {
        return deleteFilesInDir(getFileByPath(dirPath));
    }

    private static boolean deleteFilesInDir(final File dir) {
        if (dir == null) return false;
        //目录不存在返回true
        if (!dir.exists()) return true;
        //不是目录返回false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                }
                if (!deleteDir(file)) return false;
            }
        }
        return true;
    }

    private static boolean deleteDir(final File dir) {
        if (dir == null) return false;
        //目录不存在true
        if (!dir.exists()) return true;
        //不是目录返回false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }

            }
        }
        return dir.delete();
    }

    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
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
