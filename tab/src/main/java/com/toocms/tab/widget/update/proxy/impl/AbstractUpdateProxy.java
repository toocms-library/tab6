package com.toocms.tab.widget.update.proxy.impl;

import androidx.annotation.NonNull;

import com.toocms.tab.widget.update._XUpdate;
import com.toocms.tab.widget.update.proxy.IUpdateProxy;

import static com.toocms.tab.widget.update.entity.UpdateError.ERROR.CHECK_NO_NEW_VERSION;

/**
 * 简单的版本更新代理
 *
 * @author xuexiang
 * @since 2018/7/1 下午9:47
 */
public abstract class AbstractUpdateProxy implements IUpdateProxy {

    @Override
    public void onBeforeCheck() {

    }

    @Override
    public void onAfterCheck() {

    }

    @Override
    public void noNewVersion(@NonNull Throwable throwable) {
        _XUpdate.onUpdateError(CHECK_NO_NEW_VERSION, throwable.getMessage());
    }
}
