package com.toocms.tab.widget.update.proxy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.toocms.tab.widget.update.entity.UpdateEntity;
import com.toocms.tab.widget.update.service.OnFileDownloadListener;

/**
 * 版本更新下载器
 *
 * @author xuexiang
 * @since 2018/6/29 下午8:31
 */
public interface IUpdateDownloader {

    /**
     * 开始下载更新
     *
     * @param updateEntity     更新信息
     * @param downloadListener 文件下载监听
     */
    void startDownload(@NonNull UpdateEntity updateEntity, @Nullable OnFileDownloadListener downloadListener);

    /**
     * 取消下载
     */
    void cancelDownload();

    /**
     * 后台下载更新
     */
    void backgroundDownload();
}
