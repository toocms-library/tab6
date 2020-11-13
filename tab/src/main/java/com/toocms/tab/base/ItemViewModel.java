package com.toocms.tab.base;

import androidx.annotation.NonNull;

/**
 * Author：Zero
 * Date：2020/10/17 18:13
 */
public class ItemViewModel<VM extends BaseViewModel> {

    protected VM viewModel;

    public ItemViewModel(@NonNull VM viewModel) {
        this.viewModel = viewModel;
    }
}
