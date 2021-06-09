package com.toocms.tab.network;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.RegexUtils;
import com.toocms.tab.TooCMSApplication;

/**
 * API接口调用类
 *
 * @author Zero
 * @version 6.0
 * Date：2020/9/30 15:18
 */
public final class ApiTool {

    public static GetApi get(@NonNull String url) {
        return GetApi.create(parseUrl(url));
    }

    public static PostFormApi post(@NonNull String url) {
        return PostFormApi.post(parseUrl(url));
    }

    public static PostJsonApi postJson(@NonNull String url) {
        return PostJsonApi.post(parseUrl(url));
    }

    public static PostJsonArrayApi postJsonArray(@NonNull String url) {
        return PostJsonArrayApi.post(parseUrl(url));
    }

    static String parseUrl(String url) {
        return RegexUtils.isURL(url) ? url : TooCMSApplication.getInstance().getAppConfig().getBaseUrl() + url;
    }
}
