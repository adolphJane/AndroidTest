package com.magicalrice.adolph.base_utils.app_utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * Created by Adolph on 2018/1/30.
 */

public class AppManager {
    @SuppressLint("StaticFieldLeak")
    private static Application mApp;
    private static Stack<Activity> activityStack;
    private static AppManager instance;
    private static WeakReference<Activity> mTopActivityWeakRef;

    public AppManager() {
        activityStack = new Stack<>();
    }

    public void init(@NonNull final Application application) {
        AppManager.mApp = application;
        application.registerActivityLifecycleCallbacks(callbacks);
    }

    public static Application getApp() {
        return mApp;
    }

    private static Application.ActivityLifecycleCallbacks callbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            if (activityStack == null) {
                activityStack = new Stack<>();
            }
            activityStack.push(activity);
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
            if (activityStack != null && activityStack.contains(activity)) {
                activityStack.remove(activity);
            }
        }
    };

    private static void setTopActivityWeakRef(Activity activity) {
        if (mTopActivityWeakRef == null || !activity.equals(mTopActivityWeakRef.get())) {
            mTopActivityWeakRef = new WeakReference<>(activity);
        }
    }

    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public Activity getTopActivity() {
        return mTopActivityWeakRef.get();
    }

    public void finishTopActivity() {
        Activity activity = activityStack.lastElement();
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    public void finishAllActivity() {
        for (int i = 0; i < activityStack.size(); i++) {
            if (null != activityStack.get(i)) {
                Activity activity = activityStack.pop();
                activity.finish();
            }
        }
        activityStack.clear();
    }


    public void exitApp() {
        try {
            finishAllActivity();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {

        }
    }

    public Activity getActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }
}