package com.toocms.sample.ui.expand;

import com.toocms.sample.R;
import com.toocms.sample.ui.base.BaseTabItemFgt;
import com.toocms.sample.ui.base.TabItem;
import com.toocms.sample.ui.expand.guide.GuideAty;
import com.toocms.sample.ui.expand.multi.MultiLayoutFgt;
import com.toocms.sample.ui.expand.splash.SplashAty;
import com.toocms.sample.ui.widget.adder.AdderFgt;
import com.toocms.sample.ui.widget.banner.BannerFgt;
import com.toocms.tab.widget.payui.PayUI;

/**
 * Author：Zero
 * Date：2020/10/13 15:44
 */
public class ExpandFgt extends BaseTabItemFgt {

    @Override
    protected void onFragmentCreated() {
        super.onFragmentCreated();
        topBar.setTitle("拓展");
    }

    @Override
    protected TabItem[] getTabItems() {
        return new TabItem[]{
                new TabItem(R.drawable.ic_expand_tab_item_multilayout, "多布局列表", MultiLayoutFgt.class),
                new TabItem(R.drawable.ic_expand_tab_item_splash, "启动页", () -> startActivity(SplashAty.class)),
                new TabItem(R.drawable.ic_expand_tab_item_guide, "引导页", () -> startActivity(GuideAty.class)),
        };
    }
}
