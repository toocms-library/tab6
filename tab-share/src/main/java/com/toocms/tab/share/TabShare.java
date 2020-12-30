package com.toocms.tab.share;

import com.toocms.tab.share.login.OneKeyLogin;
import com.toocms.tab.share.share.OneKeyShare;

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

    /**
     * 释放
     */
    public static void release() {
        getOneKeyLogin().release();
        getOneKeyShare().release();
    }
}
