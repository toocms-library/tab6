package com.toocms.sample.ui.base;

import com.toocms.sample.BR;
import com.toocms.sample.R;
import com.toocms.sample.databinding.BaseFgtTabItemBinding;
import com.toocms.tab.TooCMSApplication;
import com.toocms.tab.widget.adapter.BindingRecyclerViewAdapter;

/**
 * Author：Zero
 * Date：2020/10/13 15:59
 */
public abstract class BaseTabItemFgt extends BaseFgt<BaseFgtTabItemBinding, TabViewModel> {

    @Override
    protected void onFragmentCreated() {
        topBar.removeAllLeftViews();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.base_fgt_tab_item;
    }

    @Override
    public int getVariableId() {
        return BR.tabViewModel;
    }

    @Override
    protected TabViewModel getViewModel() {
        return new TabViewModel(TooCMSApplication.getInstance());
    }

    @Override
    protected void viewObserver() {
        viewModel.setTabItems(getTabItems());
    }

    protected abstract TabItem[] getTabItems();
}
