package com.toocms.tab.widget.banner;

import android.content.Context;
import android.util.AttributeSet;

import com.toocms.tab.R;
import com.toocms.tab.widget.banner.base.BaseImageBanner;

/**
 * 简单的图片轮播
 *
 * @author xuexiang
 * @since 2019/1/14 下午10:07
 */
public class SimpleImageBanner extends BaseImageBanner<SimpleImageBanner> {

    public SimpleImageBanner(Context context) {
        super(context);
    }

    public SimpleImageBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleImageBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.base_layout_simple_image;
    }

    @Override
    protected int getImageViewId() {
        return R.id.iv;
    }

}
