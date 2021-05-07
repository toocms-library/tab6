package com.toocms.sample.ui.expand.push;

import com.toocms.sample.BR;
import com.toocms.sample.R;
import com.toocms.sample.databinding.FgtExpandPushBinding;
import com.toocms.sample.ui.base.BaseFgt;

/**
 * Author：Zero
 * Date：2021/4/29
 */
public class PushFgt extends BaseFgt<FgtExpandPushBinding, PushViewModel> {

    @Override
    protected void onFragmentCreated() {
        topBar.setTitle("推送");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_expand_push;
    }

    @Override
    public int getVariableId() {
        return BR.pushViewModel;
    }

    @Override
    protected void viewObserver() {

    }
}
