package com.toocms.tab.share.listener;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 分享监听
 * <p>
 * Author：Zero
 * Date：2020/5/26
 *
 * @version v3.0
 */
public abstract class OnShareListener implements UMShareListener {

    /**
     * 分享开始
     *
     * @param share_media 分享的平台
     */
    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    /**
     * 分享成功
     *
     * @param share_media 分享的平台
     */
    @Override
    public abstract void onResult(SHARE_MEDIA share_media);

    /**
     * 分享失败
     *
     * @param share_media 分享的平台
     * @param throwable   错误日志
     */
    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

    }

    /**
     * 分享取消
     *
     * @param share_media
     */
    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }
}
