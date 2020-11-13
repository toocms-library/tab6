package com.toocms.tab.widget.update.proxy.impl;

import android.text.TextUtils;

import com.toocms.tab.widget.update.XUpdate;
import com.toocms.tab.widget.update.entity.CheckVersionResult;
import com.toocms.tab.widget.update.entity.UpdateEntity;
import com.toocms.tab.widget.update.utils.UpdateUtils;

/**
 * 默认版本更新解析器
 *
 * @author xuexiang
 * @since 2018/7/5 下午4:36
 */
public class DefaultUpdateParser extends AbstractUpdateParser {

    @Override
    public UpdateEntity parseJson(String json) {
        if (!TextUtils.isEmpty(json)) {
            CheckVersionResult checkResult = UpdateUtils.fromJson(json, CheckVersionResult.class);
            if (checkResult != null && checkResult.getCode() == 0) {
                checkResult = doLocalCompare(checkResult);

                UpdateEntity updateEntity = new UpdateEntity();
                if (checkResult.getUpdateStatus() == CheckVersionResult.NO_NEW_VERSION) {
                    updateEntity.setHasUpdate(false);
                } else {
                    if (checkResult.getUpdateStatus() == CheckVersionResult.HAVE_NEW_VERSION_FORCED_UPLOAD) {
                        updateEntity.setForce(true);
                    }
                    updateEntity.setHasUpdate(true)
                            .setUpdateContent(checkResult.getModifyContent())
                            .setVersionCode(checkResult.getVersionCode())
                            .setVersionName(checkResult.getVersionName())
                            .setDownloadUrl(checkResult.getDownloadUrl())
                            .setSize(checkResult.getApkSize())
                            .setMd5(checkResult.getApkMd5());
                }
                return updateEntity;
            }
        }
        return null;
    }

    /**
     * 进行本地版本判断[防止服务端出错，本来是不需要更新，但是服务端返回是需要更新]
     *
     * @param checkResult
     * @return
     */
    private CheckVersionResult doLocalCompare(CheckVersionResult checkResult) {
        if (checkResult.getUpdateStatus() != CheckVersionResult.NO_NEW_VERSION) { //服务端返回需要更新
            int lastVersionCode = checkResult.getVersionCode();
            if (lastVersionCode <= UpdateUtils.getVersionCode(XUpdate.getContext())) { //最新版本小于等于现在的版本，不需要更新
                checkResult.setRequireUpgrade(CheckVersionResult.NO_NEW_VERSION);
            }
        }
        return checkResult;
    }
}
