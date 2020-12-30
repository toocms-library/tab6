package com.toocms.tab.map.choosing.poi.near;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.amap.api.services.core.PoiItem;
import com.toocms.tab.base.ItemViewModel;
import com.toocms.tab.binding.command.BindingCommand;
import com.toocms.tab.bus.Messenger;
import com.toocms.tab.map.choosing.TooCMSChoosingApi;

/**
 * Author：Zero
 * Date：2020/12/15 18:17
 */
public class NearPoiItemViewModel extends ItemViewModel<ObtainNearPoiViewModel> {

    public PoiItem poiItem;
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> address = new ObservableField<>();

    public NearPoiItemViewModel(@NonNull ObtainNearPoiViewModel viewModel, PoiItem poiItem) {
        super(viewModel);
        this.poiItem = poiItem;
        name.set(poiItem.getTitle());
        address.set(TextUtils.isEmpty(poiItem.getSnippet()) ? poiItem.getCityName() + poiItem.getAdName() : poiItem.getSnippet());
    }

    public BindingCommand itemClick = new BindingCommand(() -> {
        viewModel.finishFragment();
        Messenger.getDefault().send(poiItem, TooCMSChoosingApi.MESSENGER_TOKEN_NEAR_POI);
    });
}
