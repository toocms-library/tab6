package com.toocms.tab.configs;

import android.app.Application;

/**
 * App配置接口
 * <p>
 * Author Zero
 * Data 2020/11/06
 */
public interface IAppConfig {

    /**
     * 获取Http主URL
     *
     * @return
     */
    String getBaseUrl();

    /**
     * 获取更新的URL
     *
     * @return
     */
    String getUpdateUrl();

    /**
     * 获取友盟的AppKey
     *
     * @return
     */
    String getUmengAppkey();

    /**
     * 获取友盟推送服务的Secret
     *
     * @return
     */
    String getUmengPushSecret();

    /**
     * 在WeApplication中做第三方Jar包的初始化操作
     */
    void initJarForWeApplication(Application application);
}
