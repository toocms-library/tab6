package com.toocms.tab.network;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ObjectUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

/**
 * POST请求方式，jsonArray请求体
 * <p>
 * Author：Zero
 * Date：2020/9/30 15:20
 */
public class PostJsonArrayApi {

    private RxHttpJsonArrayParam param;

    private PostJsonArrayApi(RxHttpJsonArrayParam param) {
        this.param = param;
    }

    public static PostJsonArrayApi post(@NonNull String url) {
        return new PostJsonArrayApi(RxHttp.postJsonArray(url));
    }

    public PostJsonArrayApi params(HttpParams params) {
        if (ObjectUtils.isNotEmpty(params)) {
            param.addAll(params.urlParamsMap);
        }
        return this;
    }

    public PostJsonArrayApi params(Map<String, ?> params) {
        if (ObjectUtils.isNotEmpty(params)) {
            param.addAll(params);
        }
        return this;
    }

    public PostJsonArrayApi add(String key, Object value) {
        param.add(key, value);
        return this;
    }

    public PostJsonArrayApi addAll(Map<String, ?> map) {
        param.addAll(map);
        return this;
    }

    public PostJsonArrayApi addAll(String jsonObject) {
        param.addAll(jsonObject);
        return this;
    }

    public PostJsonArrayApi addAll(List<?> list) {
        param.addAll(list);
        return this;
    }

    public PostJsonArrayApi addAll(JsonArray jsonArray) {
        param.addAll(jsonArray);
        return this;
    }

    public PostJsonArrayApi addAll(JsonObject jsonObject) {
        param.addAll(jsonObject);
        return this;
    }

    /**
     * 此次请求添加请求头部
     *
     * @param key
     * @param value
     * @return
     */
    public PostJsonArrayApi addHeader(String key, String value) {
        param.addHeader(key, value);
        return this;
    }

    public PostJsonArrayApi addHeader(String key, String value, boolean isAdd) {
        param.addHeader(key, value, isAdd);
        return this;
    }

    public PostJsonArrayApi addAllHeader(Map<String, String> headers) {
        param.addAllHeader(headers);
        return this;
    }

    public PostJsonArrayApi setHeader(String key, String value) {
        param.setHeader(key, value);
        return this;
    }

    public PostJsonArrayApi setAllHeader(Map<String, String> headers) {
        param.setAllHeader(headers);
        return this;
    }

    /**
     * 设置此次请求要是否添加公共参数
     *
     * @param enabled true - 添加，false - 不添加
     * @return
     */
    public PostJsonArrayApi setAssemblyEnabled(boolean enabled) {
        param.setAssemblyEnabled(enabled);
        return this;
    }

    /**
     * 结果解析成对象类型
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T> TooCMSObservable<T> asTooCMSResponse(Class<T> cls) {
        return TooCMSObservable.create(param).asTooCMSResponse(cls);
    }

    /**
     * 结果解析成列表类型
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T> TooCMSObservable<List<T>> asTooCMSResponseList(Class<T> cls) {
        return TooCMSObservable.create(param).asTooCMSResponseList(cls);
    }
}
