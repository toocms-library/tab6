package com.toocms.tab.widget.update.proxy.impl;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.toocms.tab.widget.update._XUpdate;
import com.toocms.tab.widget.update.entity.UpdateEntity;
import com.toocms.tab.widget.update.listener.IUpdateParseCallback;
import com.toocms.tab.widget.update.proxy.IUpdateChecker;
import com.toocms.tab.widget.update.proxy.IUpdateHttpService;
import com.toocms.tab.widget.update.proxy.IUpdateProxy;
import com.toocms.tab.widget.update.service.DownloadService;
import com.toocms.tab.widget.update.utils.UpdateUtils;

import java.util.Map;

import static com.toocms.tab.widget.update.entity.UpdateError.ERROR.CHECK_JSON_EMPTY;
import static com.toocms.tab.widget.update.entity.UpdateError.ERROR.CHECK_NET_REQUEST;
import static com.toocms.tab.widget.update.entity.UpdateError.ERROR.CHECK_PARSE;
import static com.toocms.tab.widget.update.entity.UpdateError.ERROR.CHECK_UPDATING;

/**
 * 默认版本更新检查者
 *
 * @author xuexiang
 * @since 2018/7/2 下午10:21
 */
public class DefaultUpdateChecker implements IUpdateChecker {

    @Override
    public void onBeforeCheck() {

    }

    @Override
    public void checkVersion(boolean isGet, @NonNull String url, @NonNull Map<String, Object> params, final @NonNull IUpdateProxy updateProxy) {
        if (DownloadService.isRunning() || _XUpdate.isShowUpdatePrompter()) {
            updateProxy.onAfterCheck();
            _XUpdate.onUpdateError(CHECK_UPDATING);
            return;
        }

        if (isGet) {
            updateProxy.getIUpdateHttpService().asyncGet(url, params, new IUpdateHttpService.Callback() {
                @Override
                public void onSuccess(String result) {
                    onCheckSuccess(result, updateProxy);
                }

                @Override
                public void onError(Throwable error) {
                    onCheckError(updateProxy, error);
                }
            });
        } else {
            updateProxy.getIUpdateHttpService().asyncPost(url, params, new IUpdateHttpService.Callback() {
                @Override
                public void onSuccess(String result) {
                    onCheckSuccess(result, updateProxy);
                }

                @Override
                public void onError(Throwable error) {
                    onCheckError(updateProxy, error);
                }
            });
        }
    }

    @Override
    public void onAfterCheck() {

    }

    /**
     * 查询成功
     *
     * @param result
     * @param updateProxy
     */
    private void onCheckSuccess(String result, @NonNull IUpdateProxy updateProxy) {
        updateProxy.onAfterCheck();
        if (!TextUtils.isEmpty(result)) {
            processCheckResult(result, updateProxy);
        } else {
            _XUpdate.onUpdateError(CHECK_JSON_EMPTY);
        }
    }

    /**
     * 查询失败
     *
     * @param updateProxy
     * @param error
     */
    private void onCheckError(@NonNull IUpdateProxy updateProxy, Throwable error) {
        updateProxy.onAfterCheck();
        _XUpdate.onUpdateError(CHECK_NET_REQUEST, error.getMessage());
    }

    @Override
    public void processCheckResult(final @NonNull String result, final @NonNull IUpdateProxy updateProxy) {
        try {
            if (updateProxy.isAsyncParser()) {
                //异步解析
                updateProxy.parseJson(result, new IUpdateParseCallback() {
                    @Override
                    public void onParseResult(UpdateEntity updateEntity) {
                        try {
                            UpdateUtils.processUpdateEntity(updateEntity, result, updateProxy);
                        } catch (Exception e) {
                            e.printStackTrace();
                            _XUpdate.onUpdateError(CHECK_PARSE, e.getMessage());
                        }
                    }
                });
            } else {
                //同步解析
                UpdateUtils.processUpdateEntity(updateProxy.parseJson(result), result, updateProxy);
            }
        } catch (Exception e) {
            e.printStackTrace();
            _XUpdate.onUpdateError(CHECK_PARSE, e.getMessage());
        }
    }
}
