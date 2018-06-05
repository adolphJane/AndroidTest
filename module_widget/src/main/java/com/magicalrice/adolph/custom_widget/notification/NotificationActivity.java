package com.magicalrice.adolph.custom_widget.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.magicalrice.adolph.common.base.BaseActivity;
import com.magicalrice.adolph.custom_widget.R;

/**
 * Created by Adolph on 2018/2/9.
 */

@Route(path = "/widget/notification")
public class NotificationActivity extends BaseActivity implements View.OnClickListener {

    protected Button normalNotify, clearNotify,customNotify,downloadingNotify;
    protected RelativeLayout rlView;
    private NotificationManager manager;
    private long[] vibrate = new long[]{0, 500, 1000, 1500};
    private int i = 0;

    @Override
    protected int getContentViewId() {
        return R.layout.w_activity_notification;
    }

    @Override
    protected void initUI() {
        rlView = findViewById(R.id.rl_view);
        normalNotify = findViewById(R.id.normal_notify);
        clearNotify = findViewById(R.id.clear_notify);
        downloadingNotify = findViewById(R.id.downloading_notify);
        customNotify = (Button) findViewById(R.id.custom_notify);
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
        downloadingNotify.setOnClickListener(this);
        customNotify.setOnClickListener(NotificationActivity.this);
    }

    @Override
    protected String getDemoName() {
        return null;
    }

    @Override
    protected void setBase() {

    }

    private void showNormalNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setTicker("通知来啦")
                /**
                 *
                 * MAX	重要而紧急的通知，通知用户这个事件是时间上紧迫的或者需要立即处理的。
                 * HIGH	高优先级用于重要的通信内容，例如短消息或者聊天，这些都是对用户来说比较有兴趣的。
                 * DEFAULT	默认优先级用于没有特殊优先级分类的通知。
                 * LOW	低优先级可以通知用户但又不是很紧急的事件。
                 * MIN	用于后台消息 (例如天气或者位置信息)。最低优先级通知将只在状态栏显示图标，只有用户下拉通知抽屉才能看到内容。
                 */
                .setPriority(Notification.PRIORITY_DEFAULT)
                //通知产生的时间，会在通知信息里显示
                .setWhen(System.currentTimeMillis())
                //设置这个标志当用户点击通知可以让通知自动消失
                //设置它为一个正在进行的通知
                .setOngoing(false)
                .setAutoCancel(true)
                //设置通知集合的数量
                .setNumber(2)
                .setContentTitle("普通通知栏")
                .setContentText("Just Test.Hello!")
                .setFullScreenIntent(pendingIntent, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1", "channel", NotificationManager.IMPORTANCE_HIGH);
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
            builder.setLights(Color.BLUE, 3000, 3000);
        }
        Notification notification = builder.build();
        //三色灯提醒，在使用三色灯提醒时候必须加该标志符
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        /**
         * Notification.FLAG_ONGOING_EVENT          //发起正在运行事件（活动中）
         * Notification.FLAG_INSISTENT   //让声音、振动无限循环，直到用户响应 （取消或者打开）
         * Notification.FLAG_ONLY_ALERT_ONCE  //发起Notification后，铃声和震动均只执行一次
         * Notification.FLAG_AUTO_CANCEL      //用户单击通知后自动消失
         * Notification.FLAG_NO_CLEAR          //只有全部清除时，Notification才会清除 ，不清楚该通知(QQ的通知无法清除，就是用的这个)
         * Notification.FLAG_FOREGROUND_SERVICE    //表示正在运行的服务
         */
        i++;
        manager.notify(i, builder.build());
    }

    private void showLoadingNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "2");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent)
                .setContentTitle("下载通知栏")
                .setContentText("正在下载")
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.ic_notification);
        manager.notify(i, builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("2", "download", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        } else {

        }
        new Thread(() -> {
            for (int incr = 0; incr <= 100; incr++) {
                builder.setProgress(100, incr, false);
                builder.setContentText("下载" + incr + "%");
                manager.notify(i, builder.build());
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            builder.setContentTitle("开始安装")
                    .setContentText("")
                    .setProgress(0, 0, false);
            manager.notify(i, builder.build());
            i++;
        }).start();
    }

    /**
     * Notification的自定义布局是RemoteViews，和其他RemoteViews一样，在自定义视图布局文件中，
     * 仅支持FrameLayout、LinearLayout、RelativeLayout三种布局控件和AnalogClock、Chronometer、
     * Button、ImageButton、ImageView、ProgressBar、TextView、ViewFlipper、ListView、GridView、
     * StackView和AdapterViewFlipper这些显示控件，不支持这些类的子类或Android提供的其他控件。
     * 否则会引起ClassNotFoundException异常
     */
    private void showCustomNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"3");
        RemoteViews views = new RemoteViews(getPackageName(),R.layout.w_remoteview_notification);
        Intent intentPlay = new Intent("play");
        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(this,0,intentPlay,0);
        Intent intentNext = new Intent("next");
        PendingIntent pendingIntentNext = PendingIntent.getBroadcast(this,0,intentNext,0);
        views.setOnClickPendingIntent(R.id.btn_play,pendingIntentPlay);
        views.setOnClickPendingIntent(R.id.btn_next,pendingIntentNext);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("3","custom",NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        builder.setContent(views)
                .setWhen(System.currentTimeMillis())
                .setTicker("正在播放")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher_round);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        manager.notify(i,notification);
        i++;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.normal_notify) {
            if (NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                showNormalNotification();
            } else {
                showShortToast("通知栏没有权限");
            }
        } else if (view.getId() == R.id.downloading_notify) {
            if (NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                showLoadingNotification();
            } else {
                showShortToast("通知栏没有权限");
            }
        } else if (view.getId() == R.id.custom_notify) {
            if (NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                showCustomNotification();
            } else {
                showShortToast("通知栏没有权限");
            }
        } else if (view.getId() == R.id.clear_notify) {
            if (manager != null) {
                manager.cancelAll();
            }
        }
    }

    private void initView() {
        normalNotify = (Button) findViewById(R.id.normal_notify);
        normalNotify.setOnClickListener(NotificationActivity.this);
        downloadingNotify = (Button) findViewById(R.id.downloading_notify);
        downloadingNotify.setOnClickListener(NotificationActivity.this);
        clearNotify = (Button) findViewById(R.id.clear_notify);
        clearNotify.setOnClickListener(NotificationActivity.this);
        rlView = (RelativeLayout) findViewById(R.id.rl_view);

    }
}
