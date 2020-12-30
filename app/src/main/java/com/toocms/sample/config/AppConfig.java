package com.toocms.sample.config;

import android.app.Application;

import com.toocms.tab.configs.IAppConfig;
import com.umeng.socialize.PlatformConfig;

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
        PlatformConfig.setWeixin("wx0f7b622f264f30f5", "c5da5d5cc432d38358a850ad20f4f9fc");
        PlatformConfig.setSinaWeibo("1008244763", "bd40713f78c08084bcdc3b49c358fb1b", "http://sns.whalecloud.com");
        // 注册推送服务
//        TabPush.getInstance(application).register(true);
//        RxHttp.init(createClient(), true);
    }
}
