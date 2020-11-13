package com.toocms.sample.ui.widget.adder;

import com.toocms.sample.BR;
import com.toocms.sample.R;
import com.toocms.sample.databinding.FgtWidgetAdderBinding;
import com.toocms.sample.ui.base.BaseFgt;

/**
 * Author：Zero
 * Date：2020/11/3 14:08
 */
public class AdderFgt extends BaseFgt<FgtWidgetAdderBinding, AdderViewModel> {

    @Override
    protected void onFragmentCreated() {
        topBar.setTitle("图片选择器");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_widget_adder;
    }

    @Override
    public int getVariableId() {
        return BR.adderViewModel;
    }

    @Override
    protected void viewObserver() {
        viewModel.localMedia = binding.adder.getSelectList();
    }
}
