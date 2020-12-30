package com.toocms.sample.ui.expand.pay;

import com.toocms.sample.BR;
import com.toocms.sample.R;
import com.toocms.sample.databinding.FgtWidgetPayBinding;
import com.toocms.sample.ui.base.BaseFgt;

/**
 * 支付
 * Author：Zero
 * Date：2020/12/12 16:40
 */
public class PayFgt extends BaseFgt<FgtWidgetPayBinding, PayViewModel> {

    @Override
    protected void onFragmentCreated() {
        topBar.setTitle("支付");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_widget_pay;
    }

    @Override
    public int getVariableId() {
        return BR.payViewModel;
    }

    @Override
    protected void viewObserver() {

    }
}
