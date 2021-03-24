package com.toocms.sample.ui.expand.history;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.toocms.tab.base.ItemViewModel;

/**
 * Author：Zero
 * Date：2021/2/24
 */
public class HistoryItemViewModel extends ItemViewModel<HistoryViewModel> {

    public ObservableField<String> item = new ObservableField<>();

    public HistoryItemViewModel(@NonNull HistoryViewModel viewModel, String item) {
        super(viewModel);
        this.item.set(item);
    }
}
