package com.toocms.tab.network;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ObjectUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.entity.Progress;

/**
 * GET请求方式
 * <p>
 * Author：Zero
 * Date：2020/9/30
 */
public class GetApi {

    private RxHttpNoBodyParam param;

    private GetApi(RxHttpNoBodyParam param) {
        this.param = param;
    }

    public static GetApi create(@NonNull String url) {
        return new GetApi(RxHttp.get(url));
    }

    public GetApi params(HttpParams params) {
        if (ObjectUtils.isNotEmpty(params)) {
            param.addAll(params.urlParamsMap);
        }
        return this;
    }

    public GetApi params(Map<String, ?> params) {
        if (ObjectUtils.isNotEmpty(params)) {
            param.addAll(params);
        }
        return this;
    }

    public GetApi add(String key, Object value) {
        param.add(key, value);
        return this;
    }

    /**
     * 此次请求添加请求头部
     *
     * @param key
     * @param value
     * @return
     */
    public GetApi addHeader(String key, String value) {
        param.addHeader(key, value);
        return this;
    }

    public GetApi addHeader(String key, String value, boolean isAdd) {
        param.addHeader(key, value, isAdd);
        return this;
    }

    public GetApi addAllHeader(Map<String, String> headers) {
        param.addAllHeader(headers);
        return this;
    }

    public GetApi setHeader(String key, String value) {
        param.setHeader(key, value);
        return this;
    }

    public GetApi setAllHeader(Map<String, String> headers) {
        param.setAllHeader(headers);
        return this;
    }

    /**
     * 设置此次请求要是否添加公共参数
     *
     * @param enabled true - 添加，false - 不添加
     * @return
     */
    public GetApi setAssemblyEnabled(boolean enabled) {
        param.setAssemblyEnabled(enabled);
        return this;
    }

    /**
     * 下载
     *
     * @param destPath         下载到的本地路径
     * @param progressConsumer 进度回调
     * @return
     */
    public DownloadObservable asDownload(String destPath, Consumer<Progress> progressConsumer) {
        return DownloadObservable.create(param).asDownload(destPath, progressConsumer);
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
