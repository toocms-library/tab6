package com.toocms.tab.widget.update.service;

import androidx.annotation.NonNull;

import com.toocms.tab.network.ApiTool;
import com.toocms.tab.widget.update.proxy.IUpdateHttpService;
import com.toocms.tab.widget.update.utils.FileUtils;

import java.util.Map;

import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Author：Zero
 * Date：2020/4/22 11:31
 *
 * @version v5.0
 */
public class TooCMSUpdateHttpService implements IUpdateHttpService {

    private Disposable disposable;

    @Override
    public void asyncGet(@NonNull String url, @NonNull Map<String, Object> params, @NonNull Callback callBack) {
        ApiTool.get(url)
                .params(params)
                .asTooCMSResponse(String.class)
                .request(callBack::onSuccess,
                        callBack::onError);
    }

    @Override
    public void asyncPost(@NonNull String url, @NonNull Map<String, Object> params, @NonNull Callback callBack) {
        ApiTool.post(url)
                .params(params)
                .asTooCMSResponse(String.class)
                .request(callBack::onSuccess,
                        callBack::onError);
    }

    @Override
    public void download(@NonNull String url, @NonNull String path, @NonNull String fileName, @NonNull DownloadCallback callback) {
        disposable = ApiTool.get(url)
                .asDownload(path + fileName,
                        progress -> {
                            callback.onProgress(progress.getProgress() / 100f, progress.getTotalSize());
                        })
                .onStart(disposable -> callback.onStart())
                .request(s -> callback.onSuccess(FileUtils.getFileByPath(s)),
                        callback::onError);
    }

    @Override
    public void cancelDownload(@NonNull String url) {
        if (!disposable.isDisposed()) disposable.dispose();
    }
}
