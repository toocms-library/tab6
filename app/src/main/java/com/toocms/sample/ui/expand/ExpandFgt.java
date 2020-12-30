package com.toocms.sample.ui.expand;

import com.toocms.sample.R;
import com.toocms.sample.ui.base.BaseTabItemFgt;
import com.toocms.sample.ui.base.TabItem;
import com.toocms.sample.ui.expand.guide.GuideAty;
import com.toocms.sample.ui.expand.map.MapFgt;
import com.toocms.sample.ui.expand.multi.MultiLayoutFgt;
import com.toocms.sample.ui.expand.splash.SplashAty;
import com.toocms.sample.ui.expand.pay.PayFgt;
import com.toocms.sample.ui.expand.share.ShareFgt;

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
                new TabItem(R.drawable.ic_expand_tab_item_pay, "支付", PayFgt.class),
                new TabItem(R.drawable.ic_expand_tab_item_share, "分享", ShareFgt.class),
                new TabItem(R.drawable.ic_expand_tab_item_poi, "地图", MapFgt.class),
        };
    }
}
