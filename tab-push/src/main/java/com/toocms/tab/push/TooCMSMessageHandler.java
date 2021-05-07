package com.toocms.tab.push;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.widget.RemoteViews;

import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;

/**
 * 推送通知消息处理
 * Author：Zero
 * Date：2020/9/7 16:04
 */
public class TooCMSMessageHandler extends UmengMessageHandler {

    @Override
    public Notification getNotification(Context context, UMessage uMessage) {
        switch (uMessage.builder_id) {
            case 1:
                Notification.Builder builder;
                if (Build.VERSION.SDK_INT >= 26) {
                    if (!UmengMessageHandler.isChannelSet) {
                        UmengMessageHandler.isChannelSet = true;
                        NotificationChannel chan = new NotificationChannel(UmengMessageHandler.PRIMARY_CHANNEL,
                                PushAgent.getInstance(context).getNotificationChannelName(),
                                NotificationManager.IMPORTANCE_DEFAULT);
                        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        if (manager != null) manager.createNotificationChannel(chan);
                    }
                    builder = new Notification.Builder(context, UmengMessageHandler.PRIMARY_CHANNEL);
                } else {
                    builder = new Notification.Builder(context);
                }
                RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
                myNotificationView.setTextViewText(R.id.notification_title, uMessage.title);
                myNotificationView.setTextViewText(R.id.notification_text, uMessage.text);
                myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, uMessage));
                myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, uMessage));
                builder.setContent(myNotificationView)
                        .setSmallIcon(getSmallIconId(context, uMessage))
                        .setTicker(uMessage.ticker)
                        .setAutoCancel(true);
                return builder.build();
            default:
                // 默认为0，若填写的builder_id不存在则使用默认。
                return super.getNotification(context, uMessage);
        }
    }
}
