package com.toocms.sample.ui.tool.image;

import com.toocms.sample.BR;
import com.toocms.sample.R;
import com.toocms.sample.databinding.FgtToolImageBinding;
import com.toocms.sample.ui.base.BaseFgt;
import com.toocms.tab.imageload.ImageLoader;

/**
 * Author：Zero
 * Date：2020/11/12 14:40
 */
public class ImageFgt extends BaseFgt<FgtToolImageBinding, ImageViewModel> {

    @Override
    protected void onFragmentCreated() {
        topBar.setTitle("图片加载");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_tool_image;
    }

    @Override
    public int getVariableId() {
        return BR.imageViewModel;
    }

    @Override
    protected void viewObserver() {
    }
}
