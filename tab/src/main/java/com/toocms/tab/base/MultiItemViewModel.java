package com.toocms.tab.base;

import androidx.annotation.NonNull;

/**
 * Author：Zero
 * Date：2020/10/19 11:45
 */
public class MultiItemViewModel<VM extends BaseViewModel> extends ItemViewModel<VM> {

    protected String type;

    public MultiItemViewModel(@NonNull VM viewModel) {
        super(viewModel);
    }

    public String getItemType() {
        return type;
    }

    public void setItemType(@NonNull String multiType) {
        this.type = multiType;
    }
}
