package com.magicalrice.adolph.custom_widget.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.orhanobut.logger.Logger;

/**
 * Created by Adolph on 2018/2/10.
 */

public class NotificationBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("play")) {
            Logger.e("Play");
        } else if (action.equals("pause")) {
            Logger.e("Pause");
        } else if (action.equals("next")) {
            Logger.e("Next");
        }
    }
}
