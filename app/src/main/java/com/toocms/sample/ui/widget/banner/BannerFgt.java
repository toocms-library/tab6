package com.toocms.sample.ui.widget.banner;

import com.toocms.sample.BR;
import com.toocms.sample.R;
import com.toocms.sample.databinding.FgtWidgetBannerBinding;
import com.toocms.sample.ui.base.BaseFgt;

/**
 * Author：Zero
 * Date：2020/11/2 10:02
 */
public class BannerFgt extends BaseFgt<FgtWidgetBannerBinding, BannerViewModel> {

    @Override
    protected void onFragmentCreated() {
        topBar.setTitle("轮播图");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_widget_banner;
    }

    @Override
    public int getVariableId() {
        return BR.bannerViewModel;
    }

    @Override
    protected void viewObserver() {
    }
}
