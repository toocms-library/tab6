package com.toocms.tab.widget.update.listener.impl;

import com.toocms.tab.widget.update.entity.UpdateError;
import com.toocms.tab.widget.update.listener.OnUpdateFailureListener;
import com.toocms.tab.widget.update.logs.UpdateLog;

/**
 * 默认的更新出错的处理(简单地打印日志）
 *
 * @author xuexiang
 * @since 2018/7/1 下午7:48
 */
public class DefaultUpdateFailureListener implements OnUpdateFailureListener {

    @Override
    public void onFailure(UpdateError error) {
        UpdateLog.e(error);
    }
}
