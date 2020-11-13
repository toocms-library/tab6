package com.toocms.sample.ui.expand.multi;

import com.toocms.sample.BR;
import com.toocms.sample.R;
import com.toocms.sample.databinding.FgtExpandMultilayoutBinding;
import com.toocms.sample.ui.base.BaseFgt;

/**
 * Author：Zero
 * Date：2020/11/6 16:56
 */
public class MultiLayoutFgt extends BaseFgt<FgtExpandMultilayoutBinding, MultiLayoutViewModel> {

    @Override
    protected void onFragmentCreated() {
        topBar.setTitle("多布局列表");
        viewModel.loadData(true);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_expand_multilayout;
    }

    @Override
    public int getVariableId() {
        return BR.multiLayoutViewModel;
    }

    @Override
    protected void viewObserver() {
        viewModel.onRefreshFinish.observe(this, v -> binding.smartRefresh.finishRefresh());
        viewModel.onLoadMoreFinifh.observe(this, v -> binding.smartRefresh.finishLoadMore(2000));  // 延迟两秒
    }
}
