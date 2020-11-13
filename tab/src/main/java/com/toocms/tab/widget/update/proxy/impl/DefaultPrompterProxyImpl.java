package com.toocms.tab.widget.update.proxy.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.toocms.tab.widget.update.entity.UpdateEntity;
import com.toocms.tab.widget.update.proxy.IPrompterProxy;
import com.toocms.tab.widget.update.proxy.IUpdateProxy;
import com.toocms.tab.widget.update.service.OnFileDownloadListener;

/**
 * 默认版本更新提示器代理
 *
 * @author xuexiang
 * @since 2020/6/9 12:19 AM
 */
public class DefaultPrompterProxyImpl implements IPrompterProxy {

    private IUpdateProxy mIUpdateProxy;

    public DefaultPrompterProxyImpl(IUpdateProxy proxy) {
        mIUpdateProxy = proxy;
    }

    @Override
    public void startDownload(@NonNull UpdateEntity updateEntity, @Nullable OnFileDownloadListener downloadListener) {
        if (mIUpdateProxy != null) {
            mIUpdateProxy.startDownload(updateEntity, downloadListener);
        }
    }

    @Override
    public void backgroundDownload() {
        if (mIUpdateProxy != null) {
            mIUpdateProxy.backgroundDownload();
        }
    }

    @Override
    public void cancelDownload() {
        if (mIUpdateProxy != null) {
            mIUpdateProxy.cancelDownload();
        }
    }

    @Override
    public void recycle() {
        if (mIUpdateProxy != null) {
            mIUpdateProxy.recycle();
            mIUpdateProxy = null;
        }
    }
}