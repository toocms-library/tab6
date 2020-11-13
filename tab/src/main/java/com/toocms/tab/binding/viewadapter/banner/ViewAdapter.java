package com.toocms.tab.binding.viewadapter.banner;

import android.content.Context;

import androidx.databinding.BindingAdapter;

import com.qmuiteam.qmui.util.QMUIResHelper;
import com.toocms.tab.R;
import com.toocms.tab.TooCMSApplication;
import com.toocms.tab.widget.banner.BannerItem;
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

    @BindingAdapter(value = {"items", "selectColor", "unSelectColor", "onItemClickListener"}, requireAll = false)
    public static void setAdapter(SimpleImageBanner banner,
                                  List<BannerItem> items,
                                  int selectColor,
                                  int unSelectColor,
                                  BaseBanner.OnItemClickListener<BannerItem> onItemClickListener) {
        Context context = banner.getContext();
        banner.setIndicatorSelectColor(selectColor == 0 ?
                QMUIResHelper.getAttrColor(context, R.attr.app_primary_color) :
                ColorUtils.getColor(context, selectColor));
        if (unSelectColor != 0)
            banner.setIndicatorUnselectColor(ColorUtils.getColor(context, unSelectColor));
        banner.setSource(items);
        if (onItemClickListener != null)
            banner.setOnItemClickListener(onItemClickListener);
        banner.startScroll();
    }

    @BindingAdapter(value = {"items", "columnNum", "selectColor", "unSelectColor", "onItemClickListener"}, requireAll = false)
    public static void setAdapter(FlipNavigationView flipNavigationView,
                                  List<NavigationItem> items,
                                  int columnNum,
                                  int selectColor,
                                  int unSelectColor,
                                  FlipNavigationView.OnItemClickListener onItemClickListener) {
        Context context = flipNavigationView.getContext();
        if (columnNum != 0)
            flipNavigationView.setColumnNum(columnNum);
        flipNavigationView.setIndicatorSelectColor(selectColor == 0 ?
                QMUIResHelper.getAttrColor(context, R.attr.app_primary_color) :
                ColorUtils.getColor(context, selectColor));
        flipNavigationView.setIndicatorUnselectColor(unSelectColor == 0 ?
                ColorUtils.getColor(context, R.color.qmui_config_color_gray_9) :
                ColorUtils.getColor(context, unSelectColor));
        flipNavigationView.setData(items);
        if (onItemClickListener != null)
            flipNavigationView.setOnItemClickListener(onItemClickListener);
        flipNavigationView.startScroll();
    }
}
