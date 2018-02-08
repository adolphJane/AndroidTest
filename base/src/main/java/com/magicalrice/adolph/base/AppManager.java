package com.magicalrice.adolph.base;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by Adolph on 2018/1/30.
 */

public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.push(activity);
    }

    public Activity getTopActivity() {
        return activityStack.lastElement();
    }

    public void finishTopActivity() {
        Activity activity = activityStack.lastElement();
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    public void removeActivity(Activity activity) {
        if (activityStack != null && activityStack.contains(activity)) {
            activityStack.remove(activity);
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