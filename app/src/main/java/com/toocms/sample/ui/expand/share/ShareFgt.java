package com.toocms.sample.ui.expand.share;

import com.toocms.sample.BR;
import com.toocms.sample.R;
import com.toocms.sample.databinding.FgtExpandShareBinding;
import com.toocms.sample.ui.base.BaseFgt;

/**
 * 分享
 * Author：Zero
 * Date：2020/12/10 15:53
 */
public class ShareFgt extends BaseFgt<FgtExpandShareBinding, ShareViewModel> {

    @Override
    protected void onFragmentCreated() {
        topBar.setTitle("分享");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_expand_share;
    }

    @Override
    public int getVariableId() {
        return BR.shareViewModel;
    }

    @Override
    protected void viewObserver() {

    }
}
