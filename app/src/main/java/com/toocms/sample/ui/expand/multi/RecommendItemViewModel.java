package com.toocms.sample.ui.expand.multi;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.toocms.tab.base.MultiItemViewModel;

import java.util.List;

/**
 * Author：Zero
 * Date：2020/11/6 17:35
 */
public class RecommendItemViewModel extends MultiItemViewModel<MultiLayoutViewModel> {

    public ObservableField<Index.RecommendsBean> recommend = new ObservableField<>();

    public RecommendItemViewModel(@NonNull MultiLayoutViewModel viewModel, Index.RecommendsBean recommend) {
        super(viewModel);
        this.recommend.set(recommend);
    }
}
