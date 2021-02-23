package com.toocms.sample.ui.widget.floatlayout;

import com.toocms.sample.BR;
import com.toocms.sample.R;
import com.toocms.sample.databinding.FgtWidgetFloatlayoutBinding;
import com.toocms.sample.ui.base.BaseFgt;

/**
 * 流式布局
 * Author：Zero
 * Date：2021/2/19
 */
public class FloatLayoutFgt extends BaseFgt<FgtWidgetFloatlayoutBinding, FloatLayoutViewModel> {

    @Override
    protected void onFragmentCreated() {
        topBar.setTitle("流式布局");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_widget_floatlayout;
    }

    @Override
    public int getVariableId() {
        return BR.floatLayoutViewModel;
    }

    @Override
    protected void viewObserver() {

    }
}
