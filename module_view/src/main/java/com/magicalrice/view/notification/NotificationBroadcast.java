package com.magicalrice.view.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.magicalrice.base_libs.LogUtils;

/**
 * Created by Adolph on 2018/2/10.
 */

public class NotificationBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("play")) {
            LogUtils.e("Play");
        } else if (action.equals("pause")) {
            LogUtils.e("Pause");
        } else if (action.equals("next")) {
            LogUtils.e("Next");
        }
    }
}
