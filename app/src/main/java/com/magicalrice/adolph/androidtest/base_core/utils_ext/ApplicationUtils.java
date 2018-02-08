package com.magicalrice.adolph.androidtest.base_core.utils_ext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.magicalrice.adolph.androidtest.base_core.TestDemoApplication;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Adolph on 2018/2/5.
 */

public class ApplicationUtils {
    @SuppressLint("StaticFieldLeak")
    private static TestDemoApplication mApp;

    static WeakReference<Activity> mTopActivityWeakRef;
    static List<Activity> mActivityList = new LinkedList<>();

    private ApplicationUtils() {
        throw new UnsupportedOperationException("ApplicationUtils can`t be initialized");
    }

    private static Application.ActivityLifecycleCallbacks callbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            mActivityList.add(activity);
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            mActivityList.remove(activity);
        }
    };

    public static void init(@NonNull final TestDemoApplication application) {
        ApplicationUtils.mApp = application;
        application.registerActivityLifecycleCallbacks(callbacks);
    }

    public static TestDemoApplication getApp() {
        if (mApp != null) return mApp;
        throw new NullPointerException("ApplicationUtils should be inited first");
    }

    private static void setTopActivityWeakRef(Activity activity) {
        if (mTopActivityWeakRef == null || !activity.equals(mTopActivityWeakRef.get())) {
            mTopActivityWeakRef = new WeakReference<>(activity);
        }
    }
}
