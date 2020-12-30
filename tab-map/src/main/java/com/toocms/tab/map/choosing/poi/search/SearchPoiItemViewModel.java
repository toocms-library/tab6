package com.toocms.tab.map.choosing.poi.search;

import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.amap.api.services.core.PoiItem;
import com.toocms.tab.base.ItemViewModel;
import com.toocms.tab.binding.command.BindingCommand;
import com.toocms.tab.bus.Messenger;
import com.toocms.tab.map.choosing.TooCMSChoosingApi;

import static com.qmuiteam.qmui.arch.QMUIFragment.RESULT_OK;

/**
 * Author：Zero
 * Date：2020/12/16 14:08
 */
public class SearchPoiItemViewModel extends ItemViewModel<ObtainSearchPoiViewModel> {

    public PoiItem poiItem;
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> address = new ObservableField<>();

    public SearchPoiItemViewModel(@NonNull ObtainSearchPoiViewModel viewModel, PoiItem poiItem) {
        super(viewModel);
        this.poiItem = poiItem;
        name.set(poiItem.getTitle());
        address.set(TextUtils.isEmpty(poiItem.getSnippet()) ? poiItem.getCityName() + poiItem.getAdName() : poiItem.getSnippet());
    }

    public BindingCommand itemClick = new BindingCommand(() -> {
        Messenger.getDefault().send(poiItem, TooCMSChoosingApi.MESSENGER_TOKEN_NEAR_POI);
        viewModel.setFragmentResult(RESULT_OK, new Intent());
        viewModel.finishFragment();
    });
}
