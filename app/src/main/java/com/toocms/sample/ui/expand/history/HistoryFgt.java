package com.toocms.sample.ui.expand.history;

import android.view.KeyEvent;

import com.toocms.sample.BR;
import com.toocms.sample.R;
import com.toocms.sample.databinding.FgtExpandHistoryBinding;
import com.toocms.sample.ui.base.BaseFgt;

/**
 * 搜索历史
 * Author：Zero
 * Date：2021/2/24
 */
public class HistoryFgt extends BaseFgt<FgtExpandHistoryBinding, HistoryViewModel> {

    @Override
    protected void onFragmentCreated() {
        topBar.setTitle("搜索历史");
        binding.search.setOnKeyListener((v, keyCode, event) -> {
            switch (event.getAction()) {
                case KeyEvent.KEYCODE_ENTER:
                    viewModel.history.saveHistory(binding.search.getText().toString());
                    break;
            }
            return false;
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_expand_history;
    }

    @Override
    public int getVariableId() {
        return BR.historyViewModel;
    }

    @Override
    protected void viewObserver() {

    }
}
