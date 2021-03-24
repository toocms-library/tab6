package com.toocms.sample.ui.expand.history;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.LogUtils;
import com.toocms.sample.BR;
import com.toocms.sample.R;
import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.binding.ItemBinding;
import com.toocms.tab.expand.history.Constants;
import com.toocms.tab.expand.history.History;

/**
 * Author：Zero
 * Date：2021/2/24
 */
public class HistoryViewModel extends BaseViewModel {

    public ObservableList<HistoryItemViewModel> list = new ObservableArrayList<>();
    public ItemBinding<HistoryItemViewModel> itemBinding = ItemBinding.of(BR.historyItemViewModel, R.layout.fgt_expand_item_history);

    public History<String> history;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        history = new History<>(application, Constants.PRIMARY_KEY, String.class)
                .setOnSearchResultListener((key, historyList) -> {
                    for (int i = 0; i < CollectionUtils.size(historyList); i++) {
                        HistoryItemViewModel itemViewModel = new HistoryItemViewModel(this, historyList.get(i));
                        list.add(itemViewModel);
                    }
                })
                .setOnSaveResultListener(isSucceed -> {

                });
        history.getHistory();
    }


}
