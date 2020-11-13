package com.toocms.sample.ui.base;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.blankj.utilcode.util.ObjectUtils;
import com.toocms.tab.base.ItemViewModel;
import com.toocms.tab.binding.command.BindingCommand;

import java.util.Objects;

/**
 * Author：Zero
 * Date：2020/10/17 18:15
 */
public class TabItemViewModel extends ItemViewModel<TabViewModel> {

    public ObservableField<TabItem> item = new ObservableField<>();

    public TabItemViewModel(@NonNull TabViewModel viewModel, TabItem tabItem) {
        super(viewModel);
        item.set(tabItem);
    }

    public BindingCommand onItemClick = new BindingCommand(() -> {
        if (ObjectUtils.isNotEmpty(item.get().cls)) {
            viewModel.startFragment(item.get().cls);
        } else {
            Objects.requireNonNull(item.get()).action.call();
        }
    });
}
