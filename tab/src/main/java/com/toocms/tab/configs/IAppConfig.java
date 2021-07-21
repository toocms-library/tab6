package com.toocms.tab.configs;

import android.app.Application;

import com.toocms.tab.network.PostFormApi;

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
     * 如果希望部分请求不回调这里，添加参数时调用setAssemblyEnabled(false)即可
     *
     * @return
     */
    Param<?> setOnParamAssembly(Param<?> param);

    /**
     * 是否初始化三方SDK（包括initJarForWeApplication回调方法）
     *
     * @return
     */
    boolean isInitializationSDK();
}
