package com.toocms.tab.network;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ObjectUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.entity.Progress;

/**
 * POST请求方式
 * <p>
 * Author：Zero
 * Date：2020/9/30 15:20
 */
public class PostApi {

    private RxHttpFormParam param;

    private PostApi(RxHttpFormParam param) {
        this.param = param;
    }

    public static PostApi post(@NonNull String url) {
        return new PostApi(RxHttp.postForm(url));
    }

    public PostApi params(HttpParams params) {
        if (ObjectUtils.isNotEmpty(params)) {
            param.addAll(params.urlParamsMap);
            for (Map.Entry<String, File> entry : params.fileParamsMap.entrySet()) {
                param.addFile(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    public PostApi params(Map<String, ?> params) {
        if (ObjectUtils.isNotEmpty(params)) {
            param.addAll(params);
        }
        return this;
    }

    public PostApi add(String key, Object value) {
        param.add(key, value);
        return this;
    }

    public PostApi addFile(String key, File value) {
        param.addFile(key, value);
        return this;
    }

    public PostApi addFile(String key, String filePath) {
        param.addFile(key, filePath);
        return this;
    }

    public PostApi asUpload(Consumer<Progress> progressConsumer) {
        param.upload(AndroidSchedulers.mainThread(), progressConsumer);
        return this;
    }

    public <T> TooCMSObservable<T> asTooCMSResponse(Class<T> cls) {
        return TooCMSObservable.create(param).asTooCMSResponse(cls);
    }

    public <T> TooCMSObservable<List<T>> asTooCMSResponseList(Class<T> cls) {
        return TooCMSObservable.create(param).asTooCMSResponseList(cls);
    }
}
