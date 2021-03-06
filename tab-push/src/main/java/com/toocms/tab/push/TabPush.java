package com.toocms.tab.push;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.toocms.tab.TooCMSApplication;
import com.toocms.tab.base.BaseFragment;
import com.toocms.tab.binding.command.BindingConsumer;
import com.toocms.tab.bus.Messenger;
import com.toocms.tab.push.activity.TooCMSPushContainerActivity;
import com.umeng.message.IUmengCallback;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.oppo.OppoRegister;
import org.android.agoo.vivo.VivoRegister;
import org.android.agoo.xiaomi.MiPushRegistar;
import org.json.JSONException;
import org.json.JSONObject;

import static com.qmuiteam.qmui.arch.QMUIFragmentActivity.QMUI_INTENT_FRAGMENT_ARG;

/**
 * 推送
 * Author：Zero
 * Date：2020/8/27 16:40
 */
public class TabPush {

    public static final String TOKEN_NOTIFY_CLICK = "TOKEN_NOTIFY_CLICK";

    private volatile static TabPush instance;

    private PushAgent pushAgent;

    private TabPush() {
        pushAgent = PushAgent.getInstance(TooCMSApplication.getInstance());
    }

    public static TabPush getInstance() {
        if (instance == null)
            synchronized (TabPush.class) {
                if (instance == null)
                    instance = new TabPush();
            }
        return instance;
    }

    /**
     * 注册推送
     */
    public void register(UmengNotificationClickHandler notificationClickHandler) {
        register(new TooCMSMessageHandler(), notificationClickHandler, s -> LogUtils.e("注册成功", s), s -> LogUtils.e("注册失败", s));
    }

    /**
     * 注册推送
     *
     * @param messageHandler           推送通知消息处理
     * @param notificationClickHandler 推送通知点击处理
     * @param onSuccess                注册成功回调
     * @param onFailure                注册失败回调
     */
    public void register(UmengMessageHandler messageHandler, UmengNotificationClickHandler notificationClickHandler, BindingConsumer<String> onSuccess, BindingConsumer<String> onFailure) {
        pushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);   //服务端控制声音
        pushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);  //客户端允许呼吸灯点亮
        pushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);    //客户端禁止振动
        pushAgent.setMessageHandler(messageHandler);
        pushAgent.setNotificationClickHandler(notificationClickHandler);
        pushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                onSuccess.call(deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                onFailure.call(s1);
            }
        });
    }

    /**
     * 注册小米推送
     *
     * @param xiaomiId
     * @param xiaomiKey
     */
    public void registerXiaoMiPush(String xiaomiId, String xiaomiKey) {
        MiPushRegistar.register(TooCMSApplication.getInstance(), xiaomiId, xiaomiKey);
    }

    /**
     * 注册华为推送
     */
    public void registerHuaWeiPush() {
        HuaWeiRegister.register(TooCMSApplication.getInstance());
    }

    /**
     * 注册OPPO推送
     *
     * @param appKey
     * @param appSecret
     */
    public void registerOppoPush(String appKey, String appSecret) {
        OppoRegister.register(TooCMSApplication.getInstance(), appKey, appSecret);
    }

    /**
     * 注册VIVO推送
     */
    public void registerVivoPush() {
        VivoRegister.register(TooCMSApplication.getInstance());
    }

    /**
     * 开启推送
     *
     * @param callback
     */
    public void enable(IUmengCallback callback) {
        pushAgent.enable(callback);
    }

    /**
     * 关闭推送
     *
     * @param callback
     */
    public void disable(IUmengCallback callback) {
        pushAgent.disable(callback);
    }

    /**
     * 获取别名API
     *
     * @return
     */
    public AliasApi getAliasApi() {
        return AliasApi.getInstance(pushAgent);
    }

    /**
     * 启动Fragment页面
     *
     * @param context
     * @param clz
     * @param bundle
     */
    public void startFragment(@NonNull Context context, @NonNull Class<? extends QMUIFragment> clz, Bundle bundle) {
        Intent intent = new Intent(context, TooCMSPushContainerActivity.class);
        intent.putExtra("fragment", clz);
        if (bundle != null)
            intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityUtils.startActivity(intent);
    }
}
