package com.toocms.tab.widget.update.proxy.impl;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.toocms.tab.widget.update.entity.PromptEntity;
import com.toocms.tab.widget.update.entity.UpdateEntity;
import com.toocms.tab.widget.update.logs.UpdateLog;
import com.toocms.tab.widget.update.proxy.IUpdatePrompter;
import com.toocms.tab.widget.update.proxy.IUpdateProxy;
import com.toocms.tab.widget.update.widget.UpdateDialogActivity;
import com.toocms.tab.widget.update.widget.UpdateDialogFragment;

/**
 * 默认的更新提示器
 *
 * @author xuexiang
 * @since 2018/7/2 下午4:05
 */
public class DefaultUpdatePrompter implements IUpdatePrompter {

    /**
     * 显示版本更新提示
     *
     * @param updateEntity 更新信息
     * @param updateProxy  更新代理
     * @param promptEntity 提示界面参数
     */
    @Override
    public void showPrompt(@NonNull UpdateEntity updateEntity, @NonNull IUpdateProxy updateProxy, @NonNull PromptEntity promptEntity) {
        Context context = updateProxy.getContext();
        if (context == null) {
            UpdateLog.e("showPrompt failed, context is null!");
            return;
        }
        if (context instanceof FragmentActivity) {
            UpdateDialogFragment.show(((FragmentActivity) context).getSupportFragmentManager(), updateEntity, new DefaultPrompterProxyImpl(updateProxy), promptEntity);
        } else {
            UpdateDialogActivity.show(context, updateEntity, new DefaultPrompterProxyImpl(updateProxy), promptEntity);
        }
    }
}
