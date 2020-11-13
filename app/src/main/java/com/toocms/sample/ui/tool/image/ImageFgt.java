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
        ImageLoader.loadUrl2Image("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=383304109,937640631&fm=11&gp=0.jpg", binding.imageCenter, 0);
        ImageLoader.loadUrl2Image("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=818918895,1070782834&fm=11&gp=0.jpg", binding.imageLeftTop, 0);
        ImageLoader.loadUrl2Image("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4277751004,3595306162&fm=26&gp=0.jpg", binding.imageLeftRight, 0);
        ImageLoader.loadUrl2Image("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4204193996,2127491495&fm=26&gp=0.jpg", binding.imageLeftBottom, 0);
        ImageLoader.loadUrl2Image("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2207936752,3352455107&fm=26&gp=0.jpg", binding.imageRightBottom, 0);
    }
}
