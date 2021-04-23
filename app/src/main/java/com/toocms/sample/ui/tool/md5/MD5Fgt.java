package com.toocms.sample.ui.tool.md5;

import com.toocms.sample.BR;
import com.toocms.sample.R;
import com.toocms.sample.databinding.FgtToolMd5Binding;
import com.toocms.sample.ui.base.BaseFgt;

/**
 * Author：Zero
 * Date：2020/10/27 16:50
 */
public class MD5Fgt extends BaseFgt<FgtToolMd5Binding, MD5ViewModel> {

    @Override
    protected void onFragmentCreated() {
        topBar.setTitle("MD5");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_tool_md5;
    }

    @Override
    public int getVariableId() {
        return BR.md5viewModel;
    }

    @Override
    protected void viewObserver() {
        viewModel.setText.observe(this, s -> binding.tvMd5.setText(s));
    }

    @Override
    protected boolean isEnableHideSoftInput() {     // 关闭隐藏键盘
        return false;
    }
}
