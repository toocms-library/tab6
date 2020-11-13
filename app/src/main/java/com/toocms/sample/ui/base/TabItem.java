package com.toocms.sample.ui.base;

import androidx.annotation.DrawableRes;

import com.toocms.tab.base.BaseFragment;
import com.toocms.tab.binding.command.BindingAction;

/**
 * Author：Zero
 * Date：2020/10/13 17:27
 */
public class TabItem {

    public TabItem(@DrawableRes int resId, String name, Class<? extends BaseFragment> cls) {
        this.resId = resId;
        this.name = name;
        this.cls = cls;
    }

    public TabItem(@DrawableRes int resId, String name, BindingAction action) {
        this.resId = resId;
        this.name = name;
        this.action = action;
    }

    public @DrawableRes
    int resId;

    public String name;

    public BindingAction action;

    public Class<? extends BaseFragment> cls;
}
