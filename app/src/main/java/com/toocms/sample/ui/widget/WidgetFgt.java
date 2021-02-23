package com.toocms.sample.ui.widget;

import com.toocms.sample.R;
import com.toocms.sample.ui.base.BaseTabItemFgt;
import com.toocms.sample.ui.base.TabItem;
import com.toocms.sample.ui.widget.adder.AdderFgt;
import com.toocms.sample.ui.widget.badge.BadgeFgt;
import com.toocms.sample.ui.widget.banner.BannerFgt;
import com.toocms.sample.ui.widget.floatlayout.FloatLayoutFgt;
import com.toocms.tab.widget.payui.PayUI;

/**
 * Author：Zero
 * Date：2020/10/13 15:49
 */
public class WidgetFgt extends BaseTabItemFgt {

    @Override
    protected void onFragmentCreated() {
        super.onFragmentCreated();
        topBar.setTitle("控件");
    }

    @Override
    protected TabItem[] getTabItems() {
        return new TabItem[]{
                new TabItem(R.drawable.ic_widget_tab_item_banner, "轮播图", BannerFgt.class),
                new TabItem(R.drawable.ic_widget_tab_item_adder, "图片添加器", AdderFgt.class),
                new TabItem(R.drawable.ic_widget_tab_item_payui, "PayUI", () -> PayUI.showPayUI("请输入支付密码", "支付100元", this::showToast)),
                new TabItem(R.drawable.ic_widget_tab_item_badge, "角标", BadgeFgt.class),
                new TabItem(R.drawable.ic_widget_tab_item_tagflow, "流式布局", FloatLayoutFgt.class)
        };
    }
}
