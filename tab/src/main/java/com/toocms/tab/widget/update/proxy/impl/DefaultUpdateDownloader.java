package com.toocms.tab.widget.update.proxy.impl;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.toocms.tab.widget.update.XUpdate;
import com.toocms.tab.widget.update.entity.UpdateEntity;
import com.toocms.tab.widget.update.proxy.IUpdateDownloader;
import com.toocms.tab.widget.update.service.DownloadService;
import com.toocms.tab.widget.update.service.OnFileDownloadListener;

/**
 * 默认版本更新下载器
 *
 * @author xuexiang
 * @since 2018/7/5 下午5:06
 */
public class DefaultUpdateDownloader implements IUpdateDownloader {

    private DownloadService.DownloadBinder mDownloadBinder;

    /**
     * 服务绑定连接
     */
    private ServiceConnection mServiceConnection;

    /**
     * 是否已绑定下载服务
     */
    private boolean mIsBound;

    @Override
    public void startDownload(final @NonNull UpdateEntity updateEntity, final @Nullable OnFileDownloadListener downloadListener) {
        DownloadService.bindService(mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mIsBound = true;
                startDownload((DownloadService.DownloadBinder) service, updateEntity, downloadListener);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mIsBound = false;
            }
        });
    }

    /**
     * 开始下载
     *
     * @param binder
     * @param updateEntity
     * @param downloadListener
     */
    private void startDownload(DownloadService.DownloadBinder binder, @NonNull UpdateEntity updateEntity, @Nullable OnFileDownloadListener downloadListener) {
        mDownloadBinder = binder;
        mDownloadBinder.start(updateEntity, downloadListener);
    }

    @Override
    public void cancelDownload() {
        if (mDownloadBinder != null) {
            mDownloadBinder.stop("取消下载");
        }
        if (mIsBound && mServiceConnection != null) {
            XUpdate.getContext().unbindService(mServiceConnection);
            mIsBound = false;
        }
    }

    /**
     * 后台下载更新
     */
    @Override
    public void backgroundDownload() {
        if (mDownloadBinder != null) {
            mDownloadBinder.showNotification();
        }
    }
}
