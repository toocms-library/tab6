package com.toocms.tab.widget.update.listener.impl;

import android.content.Context;

import androidx.annotation.NonNull;

import com.toocms.tab.widget.update._XUpdate;
import com.toocms.tab.widget.update.entity.DownloadEntity;
import com.toocms.tab.widget.update.listener.OnInstallListener;
import com.toocms.tab.widget.update.utils.ApkInstallUtils;

import java.io.File;
import java.io.IOException;

import static com.toocms.tab.widget.update.entity.UpdateError.ERROR.INSTALL_FAILED;

/**
 * 默认的apk安装监听【自定义安装监听可继承该类，并重写相应的方法】
 *
 * @author xuexiang
 * @since 2018/7/1 下午11:58
 */
public class DefaultInstallListener implements OnInstallListener {

    @Override
    public boolean onInstallApk(@NonNull Context context, @NonNull File apkFile, @NonNull DownloadEntity downloadEntity) {
        if (checkApkFile(downloadEntity, apkFile)) {
            return installApkFile(context, apkFile);
        } else {
            _XUpdate.onUpdateError(INSTALL_FAILED, "apk文件校验不通过！");
            return false;
        }
    }

    /**
     * 检验apk文件的有效性（默认是使用MD5进行校验,可重写该方法）
     *
     * @param downloadEntity 下载信息实体
     * @param apkFile        apk文件
     * @return
     */
    protected boolean checkApkFile(DownloadEntity downloadEntity, @NonNull File apkFile) {
        return downloadEntity != null && downloadEntity.isApkFileValid(apkFile);
    }

    /**
     * 安装apk文件【此处可自定义apk的安装方法,可重写该方法】
     *
     * @param context
     * @param apkFile
     * @return
     */
    protected boolean installApkFile(Context context, File apkFile) {
        try {
            return ApkInstallUtils.install(context, apkFile);
        } catch (IOException e) {
            _XUpdate.onUpdateError(INSTALL_FAILED, "获取apk的路径出错！");
        }
        return false;
    }

    @Override
    public void onInstallApkSuccess() {

    }
}
