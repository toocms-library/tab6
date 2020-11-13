package com.toocms.sample.ui.tool;

import com.toocms.sample.R;
import com.toocms.sample.ui.base.BaseTabItemFgt;
import com.toocms.sample.ui.base.TabItem;
import com.toocms.sample.ui.tool.http.HttpFgt;
import com.toocms.sample.ui.tool.image.ImageFgt;
import com.toocms.sample.ui.tool.md5.MD5Fgt;
import com.toocms.sample.ui.tool.user.UserFgt;
import com.toocms.sample.ui.widget.WidgetFgt;
import com.toocms.tab.widget.update.UpdateManager;

/**
 * Author：Zero
 * Date：2020/10/12 14:52
 */
public class ToolFgt extends BaseTabItemFgt {

    @Override
    protected void onFragmentCreated() {
        super.onFragmentCreated();
        topBar.setTitle("工具");
    }

    @Override
    protected TabItem[] getTabItems() {
        return new TabItem[]{
                new TabItem(R.drawable.ic_tool_tab_item_network, "网络请求", HttpFgt.class),
                new TabItem(R.drawable.ic_tool_tab_item_imageload, "图片加载", ImageFgt.class),
                new TabItem(R.drawable.ic_tool_tab_item_update, "版本更新", UpdateManager::checkUpdate),
                new TabItem(R.drawable.ic_tool_tab_item_md5, "MD5", MD5Fgt.class),
                new TabItem(R.drawable.ic_tool_tab_item_user, "用户信息", UserFgt.class)
        };
    }
}
