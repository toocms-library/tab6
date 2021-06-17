package com.toocms.tab.share;

import android.app.Application;

import com.toocms.tab.share.login.OneKeyLogin;
import com.toocms.tab.share.share.OneKeyShare;
import com.umeng.socialize.PlatformConfig;

/**
 * Author：Zero
 * Date：2020/7/6 15:16
 *
 * @version v1.0
 */

public class TabShare {

    /**
     * 获取登录类实例
     *
     * @return
     */
    public static OneKeyLogin getOneKeyLogin() {
        return OneKeyLogin.getInstance();
    }

    /**
     * 获取分享类实例
     *
     * @return
     */
    public static OneKeyShare getOneKeyShare() {
        return OneKeyShare.getInstance();
    }

    public static void registerWX(Application application, String appId, String appSecret) {
        PlatformConfig.setWeixin(appId, appSecret);
        PlatformConfig.setWXFileProvider(application.getPackageName() + ".fileprovider");
    }

    public static void registerQQ(Application application, String appId, String appSecret) {
        PlatformConfig.setQQZone(appId, appSecret);
        PlatformConfig.setQQFileProvider(application.getPackageName() + ".fileprovider");
    }

    /**
     * 释放
     */
    public static void release() {
        getOneKeyLogin().release();
        getOneKeyShare().release();
    }
}
