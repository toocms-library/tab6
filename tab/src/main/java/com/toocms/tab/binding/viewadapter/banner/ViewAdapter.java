package com.toocms.tab.binding.viewadapter.banner;

import android.content.Context;

import androidx.databinding.BindingAdapter;

import com.qmuiteam.qmui.util.QMUIResHelper;
import com.toocms.tab.R;
import com.toocms.tab.TooCMSApplication;
import com.toocms.tab.widget.banner.BannerItem;
import com.toocms.tab.widget.banner.RadiusImageBanner;
import com.toocms.tab.widget.banner.SimpleImageBanner;
import com.toocms.tab.widget.banner.base.BaseBanner;
import com.toocms.tab.widget.navigation.FlipNavigationView;
import com.toocms.tab.widget.navigation.NavigationItem;
import com.toocms.tab.widget.update.utils.ColorUtils;

import java.util.List;

/**
 * Author：Zero
 * Date：2020/11/9 18:03
 */
public class ViewAdapter {

    @BindingAdapter(value = {"items", "onItemClickListener"}, requireAll = false)
    public static void setAdapter(SimpleImageBanner banner,
                                  List<BannerItem> items,
                                  BaseBanner.OnItemClickListener<BannerItem> onItemClickListener) {
        banner.setSource(items);
        if (onItemClickListener != null)
            banner.setOnItemClickListener(onItemClickListener);
        banner.startScroll();
    }

    @BindingAdapter(value = {"items", "onItemClickListener"}, requireAll = false)
    public static void setAdapter(RadiusImageBanner banner,
                                  List<BannerItem> items,
                                  BaseBanner.OnItemClickListener<BannerItem> onItemClickListener) {
        banner.setSource(items);
        if (onItemClickListener != null)
            banner.setOnItemClickListener(onItemClickListener);
        banner.startScroll();
    }

    @BindingAdapter(value = {"items", "columnNum", "onItemClickListener"}, requireAll = false)
    public static void setAdapter(FlipNavigationView flipNavigationView,
                                  List<NavigationItem> items,
                                  int columnNum,
                                  FlipNavigationView.OnItemClickListener onItemClickListener) {
        flipNavigationView.setData(items);
        if (onItemClickListener != null)
            flipNavigationView.setOnItemClickListener(onItemClickListener);
        flipNavigationView.startScroll();
    }
}
