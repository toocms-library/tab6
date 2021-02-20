package com.toocms.sample.ui.widget.tagflow;

import com.toocms.sample.BR;
import com.toocms.sample.R;
import com.toocms.sample.databinding.FgtWidgetTagflowBinding;
import com.toocms.sample.ui.base.BaseFgt;

/**
 * 流式布局
 * Author：Zero
 * Date：2021/2/19
 */
public class TagFlowLayoutFgt extends BaseFgt<FgtWidgetTagflowBinding, TagFlowLayoutViewModel> {

    @Override
    protected void onFragmentCreated() {
        topBar.setTitle("流式布局");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_widget_tagflow;
    }

    @Override
    public int getVariableId() {
        return BR.tagFlowLayoutViewModel;
    }

    @Override
    protected void viewObserver() {

    }
}
