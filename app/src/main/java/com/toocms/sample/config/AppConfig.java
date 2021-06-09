package com.toocms.sample.config;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.toocms.sample.ui.expand.push.PushFgt;
import com.toocms.tab.configs.IAppConfig;
import com.toocms.tab.push.TabPush;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;

import rxhttp.wrapper.param.Method;
import rxhttp.wrapper.param.Param;

/**
 * App配置
 * Author：Zero
 * Date：2020/11/06
 */
public class AppConfig implements IAppConfig {

    @Override
    public String getBaseUrl() {
        return "http://xlg-api.uuudoo.com/";
    }

    @Override
    public String getUpdateUrl() {
        return "http://hlhd-api.uuudoo.com/system/checkVersion";
    }

    @Override
    public String getUmengAppkey() {
        return "59a3c735f43e483077001105";
    }

    @Override
    public String getUmengPushSecret() {
        return "66475e1e296572099dfc0dfe64f16771";
    }

    @Override
    public void initJarForWeApplication(Application application) {
        // 第三方账号所对应的唯一标识
        PlatformConfig.setQQZone("1105240612", "2iDzT9GvL7L9Q9bg");
        PlatformConfig.setQQFileProvider("com.toocms.sample.fileprovider");
        PlatformConfig.setWeixin("wx0f7b622f264f30f5", "c5da5d5cc432d38358a850ad20f4f9fc");
        PlatformConfig.setWXFileProvider("com.toocms.sample.fileprovider");
        PlatformConfig.setSinaWeibo("1008244763", "bd40713f78c08084bcdc3b49c358fb1b", "http://sns.whalecloud.com");
        PlatformConfig.setSinaFileProvider("com.toocms.sample.fileprovider");
        // 注册推送服务
        TabPush.getInstance().register(new UmengNotificationClickHandler() {

            @Override
            public void dealWithCustomAction(Context context, UMessage uMessage) {
                super.dealWithCustomAction(context, uMessage);
                Bundle bundle = new Bundle();
                bundle.putString("111", uMessage.extra.get("111"));
                bundle.putString("222", uMessage.extra.get("222"));
                TabPush.getInstance().startFragment(context, PushFgt.class, bundle);
            }
        });
        TabPush.getInstance().registerXiaoMiPush("2882303761519902601", "5561990252601");
    }

    @Override
    public Param<?> setOnParamAssembly(Param<?> param) {
        Method method = param.getMethod();
        if (method.isGet()) {
            param.add("method", "get");
        } else if (method.isPost()) { //Post请求
            param.add("method", "post");
        }
        return param.add("versionName", "1.0.0")//添加公共参数
                .add("time", System.currentTimeMillis())
                .addHeader("deviceType", "android"); //添加公共请求头
    }
}
