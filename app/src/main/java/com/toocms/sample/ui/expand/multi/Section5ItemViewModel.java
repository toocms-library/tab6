package com.toocms.sample.ui.expand.multi;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.toocms.tab.base.MultiItemViewModel;

/**
 * Author：Zero
 * Date：2020/11/6 17:32
 */
public class Section5ItemViewModel extends MultiItemViewModel<MultiLayoutViewModel> {

    public ObservableField<Index.SectionsBean> section = new ObservableField<>();

    public Section5ItemViewModel(@NonNull MultiLayoutViewModel viewModel, Index.SectionsBean section) {
        super(viewModel);
        this.section.set(section);
    }
}
