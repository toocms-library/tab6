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

    public DownloadObservable asDownload(String destPath, Consumer<Progress> progressConsumer) {
        return DownloadObservable.create(param).asDownload(destPath, progressConsumer);
    }

    public <T> TooCMSObservable<T> asTooCMSResponse(Class<T> cls) {
        return TooCMSObservable.create(param).asTooCMSResponse(cls);
    }

    public <T> TooCMSObservable<List<T>> asTooCMSResponseList(Class<T> cls) {
        return TooCMSObservable.create(param).asTooCMSResponseList(cls);
    }
}
