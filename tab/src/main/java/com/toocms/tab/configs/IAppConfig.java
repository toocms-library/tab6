package com.toocms.tab.configs;

import android.app.Application;

import rxhttp.wrapper.param.Param;

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

    /**
     * 设置RxHttp请求时的公共参数
     * 可根据不同请求添加不同参数，每次发送请求前都会被回调
     * 如果希望部分请求不回调这里，发请求前调用{@link com.toocms.tab.network.GetApi#setAssemblyEnabled(boolean)}、{@link com.toocms.tab.network.PostApi#setAssemblyEnabled(boolean)}为false即可
     *
     * @return
     */
    Param<?> setOnParamAssembly(Param<?> param);
}
