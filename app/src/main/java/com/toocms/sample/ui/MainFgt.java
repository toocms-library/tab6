package com.toocms.sample.ui;

import android.graphics.Color;

import com.blankj.utilcode.util.LogUtils;
import com.toocms.sample.R;
import com.toocms.sample.ui.expand.ExpandFgt;
import com.toocms.sample.ui.tool.ToolFgt;
import com.toocms.sample.ui.widget.WidgetFgt;
import com.toocms.tab.expand.tabsegment.BaseBottomTabSegmentFragment;
import com.toocms.tab.expand.tabsegment.TabSegmentItem;

/**
 * 首页
 * <p>
 * Author：Zero
 * Date：2020/9/29 18:23
 */
public class MainFgt extends BaseBottomTabSegmentFragment {

    @Override
    protected TabSegmentItem[] getTabSegmentItems() {
        LogUtils.e(Color.parseColor("#F6F4F4"));
        return new TabSegmentItem[]{
                new TabSegmentItem(R.drawable.ic_tab_tool_normal, R.drawable.ic_tab_tool_selected, "工具", ToolFgt.class),
                new TabSegmentItem(R.drawable.ic_tab_widget_normal, R.drawable.ic_tab_widget_selected, "控件", WidgetFgt.class),
                new TabSegmentItem(R.drawable.ic_tab_expand_normal, R.drawable.ic_tab_expand_selected, "拓展", ExpandFgt.class)
        };
    }

    @Override
    protected boolean isSwipeable() {
        return false;
    }
}
