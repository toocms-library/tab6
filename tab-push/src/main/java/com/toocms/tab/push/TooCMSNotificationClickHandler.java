package com.toocms.tab.push;

import android.content.Context;

import com.toocms.tab.bus.Messenger;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

/**
 * 推送通知点击处理
 * Author：Zero
 * Date：2020/9/7 16:07
 */
public class TooCMSNotificationClickHandler extends UmengNotificationClickHandler {

    @Override
    public void launchApp(Context context, UMessage uMessage) {
        super.launchApp(context, uMessage);
        Messenger.getDefault().send(uMessage, TabPush.TOKEN_NOTIFY_CLICK);
    }
}
