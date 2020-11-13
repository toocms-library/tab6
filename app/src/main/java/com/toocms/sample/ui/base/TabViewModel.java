package com.toocms.sample.ui.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.toocms.sample.BR;
import com.toocms.sample.R;
import com.toocms.tab.base.BaseModel;
import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.binding.ItemBinding;

/**
 * Author：Zero
 * Date：2020/10/17 17:10
 */
public class TabViewModel extends BaseViewModel<BaseModel> {

    public ObservableList<TabItemViewModel> tabItems = new ObservableArrayList<>();
    public ItemBinding<TabItemViewModel> itemBinding = ItemBinding.of(BR.tabItemViewModel, R.layout.item_tab);

    public TabViewModel(@NonNull Application application) {
        super(application);
    }

    public void setTabItems(TabItem[] tabItems) {
        for (TabItem tabItem : tabItems) {
            TabItemViewModel tabItemViewModel = new TabItemViewModel(this, tabItem);
            this.tabItems.add(tabItemViewModel);
        }
    }
}
