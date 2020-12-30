package com.toocms.tab.map.choosing.poi.search;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.blankj.utilcode.util.CollectionUtils;
import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.binding.ItemBinding;
import com.toocms.tab.map.BR;
import com.toocms.tab.map.R;
import com.toocms.tab.map.TabMapApi;

/**
 * Author：Zero
 * Date：2020/12/16 9:50
 */
public class ObtainSearchPoiViewModel extends BaseViewModel {

    public ObservableList<SearchPoiItemViewModel> list = new ObservableArrayList<>();
    public ItemBinding<SearchPoiItemViewModel> itemBinding = ItemBinding.of(BR.searchPoiItemViewModel, R.layout.item_search_poi);

    private String cityCode;

    public ObtainSearchPoiViewModel(@NonNull Application application) {
        super(application);
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public void doSearch(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            list.clear();
            return;
        }
        TabMapApi.getPoiApi().doSearchPoi(
                keyword,
                null,
                cityCode,
                1,
                40,
                new PoiSearch.OnPoiSearchListener() {
                    @Override
                    public void onPoiSearched(PoiResult poiResult, int i) {
                        list.clear();
                        if (CollectionUtils.isEmpty(poiResult.getPois())) {
                            showEmpty();
                            return;
                        }
                        for (int j = 0; j < CollectionUtils.size(poiResult.getPois()); j++) {
                            SearchPoiItemViewModel poiItemViewModel = new SearchPoiItemViewModel(ObtainSearchPoiViewModel.this, poiResult.getPois().get(j));
                            list.add(poiItemViewModel);
                        }
                    }

                    @Override
                    public void onPoiItemSearched(PoiItem poiItem, int i) {
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TabMapApi.release();
    }
}
