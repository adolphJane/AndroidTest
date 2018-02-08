package com.magicalrice.adolph.common.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Adolph on 2018/2/5.
 */

public class AppInfo {
    private String name;
    private Drawable icon;
    private String packageName;
    private String packagePath;
    private String versionName;
    private int versionCode;
    private boolean isSystem;

    public AppInfo(String name, Drawable icon, String packageName, String packagePath, String versionName, int versionCode, boolean isSystem) {
        this.name = name;
        this.icon = icon;
        this.packageName = packageName;
        this.packagePath = packagePath;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.isSystem = isSystem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    @Override
    public String toString() {
        return "pkg_name: " + getPackageName() +
                "\napp_name" + getName() +
                "\napp_path" + getPackagePath() +
                "\nversion_name" + getVersionName() +
                "\nversion_code" + getVersionCode() +
                "\nis_system" + isSystem();
    }
}
