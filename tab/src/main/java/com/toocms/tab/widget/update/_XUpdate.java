package com.toocms.tab.widget.update;

import android.content.Context;

import androidx.annotation.NonNull;

import com.toocms.tab.widget.update.entity.DownloadEntity;
import com.toocms.tab.widget.update.entity.UpdateError;
import com.toocms.tab.widget.update.listener.OnInstallListener;
import com.toocms.tab.widget.update.listener.OnUpdateFailureListener;
import com.toocms.tab.widget.update.listener.impl.DefaultInstallListener;
import com.toocms.tab.widget.update.listener.impl.DefaultUpdateFailureListener;
import com.toocms.tab.widget.update.logs.UpdateLog;
import com.toocms.tab.widget.update.proxy.IUpdateChecker;
import com.toocms.tab.widget.update.proxy.IUpdateDownloader;
import com.toocms.tab.widget.update.proxy.IUpdateHttpService;
import com.toocms.tab.widget.update.proxy.IUpdateParser;
import com.toocms.tab.widget.update.proxy.IUpdatePrompter;
import com.toocms.tab.widget.update.proxy.impl.DefaultFileEncryptor;
import com.toocms.tab.widget.update.utils.ApkInstallUtils;

import java.io.File;
import java.util.Map;

import static com.toocms.tab.widget.update.entity.UpdateError.ERROR.INSTALL_FAILED;

/**
 * 内部版本更新参数的获取
 *
 * @author xuexiang
 * @since 2018/7/10 下午4:27
 */
public final class _XUpdate {

    /**
     * 标志当前更新提示是否已显示
     */
    private static boolean sIsShowUpdatePrompter = false;

    public static void setIsShowUpdatePrompter(boolean isShowUpdatePrompter) {
        _XUpdate.sIsShowUpdatePrompter = isShowUpdatePrompter;
    }

    public static boolean isShowUpdatePrompter() {
        return _XUpdate.sIsShowUpdatePrompter;
    }

    //===========================属性设置===================================//

    public static Map<String, Object> getParams() {
        return XUpdate.get().mParams;
    }

    public static IUpdateHttpService getIUpdateHttpService() {
        return XUpdate.get().mIUpdateHttpService;
    }

    public static IUpdateChecker getIUpdateChecker() {
        return XUpdate.get().mIUpdateChecker;
    }

    public static IUpdateParser getIUpdateParser() {
        return XUpdate.get().mIUpdateParser;
    }

    public static IUpdatePrompter getIUpdatePrompter() {
        return XUpdate.get().mIUpdatePrompter;
    }

    public static IUpdateDownloader getIUpdateDownLoader() {
        return XUpdate.get().mIUpdateDownloader;
    }

    public static boolean isGet() {
        return XUpdate.get().mIsGet;
    }

    public static boolean isWifiOnly() {
        return XUpdate.get().mIsWifiOnly;
    }

    public static boolean isAutoMode() {
        return XUpdate.get().mIsAutoMode;
    }

    public static String getApkCacheDir() {
        return XUpdate.get().mApkCacheDir;
    }

    //===========================文件加密===================================//

    /**
     * 加密文件
     *
     * @param file 需要加密的文件
     */
    public static String encryptFile(File file) {
        if (XUpdate.get().mIFileEncryptor == null) {
            XUpdate.get().mIFileEncryptor = new DefaultFileEncryptor();
        }
        return XUpdate.get().mIFileEncryptor.encryptFile(file);
    }

    /**
     * 验证文件是否有效（加密是否一致）
     *
     * @param encrypt 加密值，不能为空
     * @param file    需要校验的文件
     * @return 文件是否有效
     */
    public static boolean isFileValid(String encrypt, File file) {
        if (XUpdate.get().mIFileEncryptor == null) {
            XUpdate.get().mIFileEncryptor = new DefaultFileEncryptor();
        }
        return XUpdate.get().mIFileEncryptor.isFileValid(encrypt, file);
    }

    //===========================apk安装监听===================================//

    public static OnInstallListener getOnInstallListener() {
        return XUpdate.get().mOnInstallListener;
    }

    /**
     * 开始安装apk文件
     *
     * @param context 传activity可以获取安装的返回值，详见{@link ApkInstallUtils#REQUEST_CODE_INSTALL_APP}
     * @param apkFile apk文件
     */
    public static void startInstallApk(@NonNull Context context, @NonNull File apkFile) {
        startInstallApk(context, apkFile, new DownloadEntity());
    }

    /**
     * 开始安装apk文件
     *
     * @param context        传activity可以获取安装的返回值，详见{@link ApkInstallUtils#REQUEST_CODE_INSTALL_APP}
     * @param apkFile        apk文件
     * @param downloadEntity 文件下载信息
     */
    public static void startInstallApk(@NonNull Context context, @NonNull File apkFile, @NonNull DownloadEntity downloadEntity) {
        UpdateLog.d("开始安装apk文件, 文件路径:" + apkFile.getAbsolutePath() + ", 下载信息:" + downloadEntity);
        if (onInstallApk(context, apkFile, downloadEntity)) {
            onApkInstallSuccess(); //静默安装的话，不会回调到这里
        } else {
            onUpdateError(INSTALL_FAILED);
        }
    }

    /**
     * 安装apk
     *
     * @param context        传activity可以获取安装的返回值，详见{@link ApkInstallUtils#REQUEST_CODE_INSTALL_APP}
     * @param apkFile        apk文件
     * @param downloadEntity 文件下载信息
     */
    private static boolean onInstallApk(Context context, File apkFile, DownloadEntity downloadEntity) {
        if (XUpdate.get().mOnInstallListener == null) {
            XUpdate.get().mOnInstallListener = new DefaultInstallListener();
        }
        return XUpdate.get().mOnInstallListener.onInstallApk(context, apkFile, downloadEntity);
    }

    /**
     * apk安装完毕
     */
    private static void onApkInstallSuccess() {
        if (XUpdate.get().mOnInstallListener == null) {
            XUpdate.get().mOnInstallListener = new DefaultInstallListener();
        }
        XUpdate.get().mOnInstallListener.onInstallApkSuccess();
    }

    //===========================更新出错===================================//

    public static OnUpdateFailureListener getOnUpdateFailureListener() {
        return XUpdate.get().mOnUpdateFailureListener;
    }

    /**
     * 更新出现错误
     *
     * @param errorCode
     */
    public static void onUpdateError(int errorCode) {
        onUpdateError(new UpdateError(errorCode));
    }

    /**
     * 更新出现错误
     *
     * @param errorCode
     * @param message
     */
    public static void onUpdateError(int errorCode, String message) {
        onUpdateError(new UpdateError(errorCode, message));
    }

    /**
     * 更新出现错误
     *
     * @param updateError
     */
    public static void onUpdateError(@NonNull UpdateError updateError) {
        if (XUpdate.get().mOnUpdateFailureListener == null) {
            XUpdate.get().mOnUpdateFailureListener = new DefaultUpdateFailureListener();
        }
        XUpdate.get().mOnUpdateFailureListener.onFailure(updateError);
    }
}
