package com.toocms.tab.share.listener;

import com.toocms.tab.share.login.PlatformUser;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * 授权/获取用户信息监听
 * <p>
 * Author：Zero
 * Date：2020/5/26
 *
 * @version v3.0
 */
public abstract class OnAuthListener implements UMAuthListener {

    /**
     * 自定义授权成功回调
     *
     * @param share_media
     * @param action
     * @param user
     */
    public abstract void onComplete(SHARE_MEDIA share_media, int action, PlatformUser user);

    /**
     * 授权开始
     *
     * @param share_media 分享的平台
     */
    @Override
    public void onStart(SHARE_MEDIA share_media) {
    }

    /**
     * 授权成功
     *
     * @param share_media 分享的平台
     * @param action      动作
     * @param map         平台用户信息
     */
    @Override
    public void onComplete(SHARE_MEDIA share_media, int action, Map<String, String> map) {
        PlatformUser user = null;
        if (map != null) {
            user = new PlatformUser(map.get("uid"), map.get("name"), map.get("gender"), map.get("iconurl"), map.get("accessToken"));
        }
        onComplete(share_media, action, user);
    }

    /**
     * 授权失败
     *
     * @param share_media 分享的平台
     * @param action      动作
     * @param throwable   错误日志
     */
    @Override
    public void onError(SHARE_MEDIA share_media, int action, Throwable throwable) {
    }

    /**
     * 授权取消
     *
     * @param share_media 分享的平台
     * @param action      动作
     */
    @Override
    public void onCancel(SHARE_MEDIA share_media, int action) {
    }
}
