package com.toocms.sample.ui.expand.multi;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.toocms.tab.base.MultiItemViewModel;
import com.toocms.tab.widget.navigation.FlipNavigationView;
import com.toocms.tab.widget.navigation.NavigationItem;

import java.util.List;

/**
 * Author：Zero
 * Date：2020/11/6 17:27
 */
public class NavigationItemViewModel extends MultiItemViewModel<MultiLayoutViewModel> {

    public ObservableList<NavigationItem> navigationItems = new ObservableArrayList<>();

    public NavigationItemViewModel(@NonNull MultiLayoutViewModel viewModel, List<Index.NavsBean> list) {
        super(viewModel);
        for (Index.NavsBean nav : list) {
            NavigationItem item = new NavigationItem();
            item.setIcon(nav.getIcon_path());
            item.setName(nav.getName());
            item.setTarget_rule(nav.getTarget_rule());
            item.setParam(nav.getParam());
            // 扩展字段
            item.setExpand1("");
            item.setExpand2("");
            item.setExpand3("");
            navigationItems.add(item);
            navigationItems.add(item);
            navigationItems.add(item);
            navigationItems.add(item);
            navigationItems.add(item);
        }
    }

    public FlipNavigationView.OnItemClickListener listener = item -> {
        viewModel.showToast(item.toString());
    };
}
