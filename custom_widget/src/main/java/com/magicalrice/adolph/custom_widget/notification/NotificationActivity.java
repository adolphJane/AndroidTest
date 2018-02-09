package com.magicalrice.adolph.custom_widget.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.magicalrice.adolph.common.base.BaseActivity;
import com.magicalrice.adolph.custom_widget.R;

/**
 * Created by Adolph on 2018/2/9.
 */

@Route(path = "/widget/notification")
public class NotificationActivity extends BaseActivity implements View.OnClickListener {

    protected Button normalNotify,clearNotify;
    protected RelativeLayout rlView;
    private NotificationManager manager;
    private long[] vibrate = new long[] {0,500,1000,1500};
    private int i = 0;

    @Override
    protected int getContentViewId() {
        return R.layout.cw_activity_notification;
    }

    @Override
    protected void initUI() {
        rlView = (RelativeLayout) findViewById(R.id.rl_view);
        normalNotify = (Button) findViewById(R.id.normal_notify);
        clearNotify = findViewById(R.id.clear_notify);
        ViewCompat.setTransitionName(rlView, "card");
    }

    @Override
    protected void initData() {
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void initListener() {
        normalNotify.setOnClickListener(this);
        clearNotify.setOnClickListener(this);
    }

    @Override
    protected String getDemoName() {
        return null;
    }

    @Override
    protected void setBase() {

    }

    private void showNormalNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"1");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setTicker("通知来啦")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setNumber(2)
                .setContentTitle("普通通知栏")
                .setContentText("Just Test.Hello!")
                .setFullScreenIntent(pendingIntent, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1","channel",NotificationManager.IMPORTANCE_HIGH);
            //设置铃声
            //channel.setSound();
            //设置震动
            channel.setVibrationPattern(vibrate);
            //设置呼吸灯
            channel.setLightColor(Color.BLUE);
            manager.createNotificationChannel(channel);
        } else {
            //设置铃声
            //builder.setSound()
            //设置震动
            builder.setVibrate(vibrate);
            builder.setLights(Color.BLUE,3000,3000);
        }
        i ++;
        manager.notify(i, builder.build());

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.normal_notify) {
            if (NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                showNormalNotification();
            } else {
                showShortToast("通知栏没有权限");
            }
        } else if (view.getId() == R.id.clear_notify) {
            if (manager != null) {
                manager.cancelAll();
            }
        }
    }
}
