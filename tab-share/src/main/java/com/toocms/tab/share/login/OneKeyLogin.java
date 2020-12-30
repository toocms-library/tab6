package com.toocms.tab.share.login;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.toocms.tab.share.listener.OnAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 一键登录
 *
 * @author Zero
 * @version 3.0
 * @date 2020/5/26
 */
public class OneKeyLogin {

    private volatile static OneKeyLogin instance;

    private Activity activity;
    private UMShareAPI umShareAPI;

    private OneKeyLogin() {
        activity = ActivityUtils.getTopActivity();
        umShareAPI = UMShareAPI.get(activity);
    }

    public static OneKeyLogin getInstance() {
        if (instance == null)
            synchronized (OneKeyLogin.class) {
                if (instance == null)
                    instance = new OneKeyLogin();
            }
        return instance;
    }

    /**
     * 获取用户信息
     *
     * @param isNeedAuth  是否每次授权都需要显示授权页
     * @param share_media 平台名称
     * @param listener    回调监听
     */
    public void showUser(boolean isNeedAuth, SHARE_MEDIA share_media, OnAuthListener listener) {
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(isNeedAuth);
        umShareAPI.setShareConfig(config);
        umShareAPI.getPlatformInfo(activity, share_media, listener);
    }

    /**
     * 撤销授权
     *
     * @param share_media
     * @param listener
     */
    public void revokeAuthorize(SHARE_MEDIA share_media, OnAuthListener listener) {
        umShareAPI.deleteOauth(activity, share_media, listener);
    }

    /**
     * 获取平台是否已经被授权
     *
     * @param share_media
     * @return
     */
    public boolean isAuthorize(@NonNull SHARE_MEDIA share_media) {
        return umShareAPI.isAuthorize(activity, share_media);
    }

    /**
     * 释放
     */
    public void release() {
        instance = null;
    }
}
