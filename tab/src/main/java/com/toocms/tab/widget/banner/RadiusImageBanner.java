package com.toocms.tab.widget.banner;

import android.content.Context;
import android.util.AttributeSet;

import com.toocms.tab.R;
import com.toocms.tab.widget.banner.base.BaseImageBanner;

/**
 * 圆角的图片轮播
 *
 * @author xuexiang
 * @since 2021/3/25 15:03
 */
public class RadiusImageBanner extends BaseImageBanner<RadiusImageBanner> {

    public RadiusImageBanner(Context context) {
        super(context);
    }

    public RadiusImageBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadiusImageBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.base_layout_radius_image;
    }

    @Override
    protected int getImageViewId() {
        return R.id.riv;
    }

}
