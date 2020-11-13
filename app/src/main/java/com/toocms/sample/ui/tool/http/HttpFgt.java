package com.toocms.sample.ui.tool.http;

import com.toocms.sample.BR;
import com.toocms.sample.R;
import com.toocms.sample.databinding.FgtToolHttpBinding;
import com.toocms.sample.ui.base.BaseFgt;

/**
 * Author：Zero
 * Date：2020/10/12 10:51
 */
public class HttpFgt extends BaseFgt<FgtToolHttpBinding, HttpViewModel> {

    @Override
    protected void onFragmentCreated() {
        topBar.setTitle("网络请求");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_tool_http;
    }

    @Override
    public int getVariableId() {
        return BR.httpViewModel;
    }

    @Override
    protected void viewObserver() {
        viewModel.getUc().setText.observe(this, s -> {
            binding.httpTextview.setText(s);
        });
    }
}
