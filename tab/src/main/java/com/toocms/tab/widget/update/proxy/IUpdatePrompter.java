package com.toocms.tab.widget.update.proxy;

import androidx.annotation.NonNull;

import com.toocms.tab.widget.update.entity.PromptEntity;
import com.toocms.tab.widget.update.entity.UpdateEntity;

/**
 * 版本更新提示器
 *
 * @author xuexiang
 * @since 2018/6/29 下午8:35
 */
public interface IUpdatePrompter {

    /**
     * 显示版本更新提示
     *
     * @param updateEntity 更新信息
     * @param updateProxy  更新代理
     * @param promptEntity 提示界面参数
     */
    void showPrompt(@NonNull UpdateEntity updateEntity, @NonNull IUpdateProxy updateProxy, @NonNull PromptEntity promptEntity);
}
