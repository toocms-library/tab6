package com.toocms.sample.ui;

import com.toocms.sample.R;
import com.toocms.sample.ui.expand.ExpandFgt;
import com.toocms.sample.ui.expand.push.PushFgt;
import com.toocms.sample.ui.tool.ToolFgt;
import com.toocms.sample.ui.widget.WidgetFgt;
import com.toocms.tab.expand.tabsegment.BaseBottomTabSegmentFragment;
import com.toocms.tab.expand.tabsegment.TabSegmentItem;
import com.toocms.tab.push.TabPush;

/**
 * 首页
 * <p>
 * Author：Zero
 * Date：2020/9/29 18:23
 */
public class MainFgt extends BaseBottomTabSegmentFragment {

    @Override
    protected void onFragmentCreated() {
        super.onFragmentCreated();
        TabPush.getInstance().handlerNotifyClick(this, uMessage -> action());
    }

    @Override
    protected TabSegmentItem[] getTabSegmentItems() {
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

    private void action() {
        startFragment(PushFgt.class);
    }
}
