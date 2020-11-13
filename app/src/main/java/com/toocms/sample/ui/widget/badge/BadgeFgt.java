package com.toocms.sample.ui.widget.badge;

import com.toocms.sample.R;
import com.toocms.sample.databinding.FgtWidgetBadgeBinding;
import com.toocms.sample.ui.base.BaseFgt;
import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.widget.badge.Badge;
import com.toocms.tab.widget.badge.BadgeView;

/**
 * Author：Zero
 * Date：2020/11/12 16:49
 */
public class BadgeFgt extends BaseFgt<FgtWidgetBadgeBinding, BaseViewModel> {

    @Override
    protected void onFragmentCreated() {
        topBar.setTitle("角标");
        Badge badge = new BadgeView(getContext());
        badge.bindTarget(binding.message);
        badge.setBadgeNumber(5);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_widget_badge;
    }

    @Override
    public int getVariableId() {
        return 0;
    }

    @Override
    protected void viewObserver() {

    }
}
