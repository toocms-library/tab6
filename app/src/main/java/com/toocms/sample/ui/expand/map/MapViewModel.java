package com.toocms.sample.ui.expand.map;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableDouble;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.blankj.utilcode.util.CollectionUtils;
import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.binding.command.BindingCommand;
import com.toocms.tab.map.TabMapApi;
import com.toocms.tab.map.location.listener.LocationListener;

import java.util.List;

/**
 * Author：Zero
 * Date：2020/12/28 16:45
 */
public class MapViewModel extends BaseViewModel {

    public MapViewModel(@NonNull Application application) {
        super(application);
    }

    public BindingCommand startLocation = new BindingCommand(() ->
            TabMapApi.getLocationApi()
//                    .setLocationOption(new AMapLocationClientOption())    // 可选项，自定义定位参数，一般不需要设置
                    .start(new LocationListener() {
                        @Override
                        public void onLocationSuccess(AMapLocation aMapLocation) {
                            showSingleActionDialog("定位结果", aMapLocation.toString(), "确定", (dialog, index) -> dialog.dismiss());
                        }

                        @Override
                        public void onLocationFail() {
                            showSingleActionDialog("定位结果", "定位失败", "确定", (dialog, index) -> dialog.dismiss());
                        }
                    })
    );

    public BindingCommand getNearestPoi = new BindingCommand(() ->
            TabMapApi.getLocationApi().start(building ->
                    showSingleActionDialog("当前位置", building.getName() + "\n" + "经度：" + building.getLongitude() + "\n" + "纬度：" + building.getLatitude(), "确定", (dialog, index) -> dialog.dismiss())));

    public BindingCommand getNearPoi = new BindingCommand(() -> {
        showProgress();
        TabMapApi.getLocationApi()
                .start(new LocationListener() {
                    @Override
                    public void onLocationSuccess(AMapLocation aMapLocation) {
                        TabMapApi.getPoiApi()
                                .doSearchPoi(
                                        aMapLocation.getLatitude(),    // 纬度
                                        aMapLocation.getLongitude(),  // 经度
                                        1000,        // 查询半径
                                        null,         // 关键字
                                        TabMapApi.DEFAULT_POI_TYPE,     // POI类型，示例字段为全类型
                                        1,           // 页码
                                        10,         // 每页返回条数
                                        new PoiSearch.OnPoiSearchListener() {
                                            @Override
                                            public void onPoiSearched(PoiResult poiResult, int i) {
                                                List<PoiItem> poiItems = poiResult.getPois();
                                                if (CollectionUtils.isEmpty(poiItems)) return;
                                                String[] pois = new String[CollectionUtils.size(poiItems)];
                                                CollectionUtils.forAllDo(poiItems, (index, item) -> pois[index] = item.getTitle() + "\n" + "地址：" + item.getSnippet() + "\n" + "距离：" + item.getDistance() + "米" + "\n");
                                                showItemsDialog("周边POI", pois, null);
                                                removeProgress();
                                            }

                                            @Override
                                            public void onPoiItemSearched(PoiItem poiItem, int i) {
                                            }
                                        });
                    }

                    @Override
                    public void onLocationFail() {
                        showSingleActionDialog("定位结果", "定位失败", "确定", (dialog, index) -> dialog.dismiss());
                    }
                });
    });

    public BindingCommand getKeyWordPoi = new BindingCommand(() -> {
        showProgress();
        TabMapApi.getPoiApi()
                .doSearchPoi(
                        "天安门",   // 关键字
                        TabMapApi.DEFAULT_POI_TYPE,     // POI类型，示例字段为全类型
                        "北京",   // 城市
                        1,           // 页码
                        10,         // 每页返回条数
                        new PoiSearch.OnPoiSearchListener() {
                            @Override
                            public void onPoiSearched(PoiResult poiResult, int i) {
                                List<PoiItem> poiItems = poiResult.getPois();
                                if (CollectionUtils.isEmpty(poiItems)) return;
                                String[] pois = new String[CollectionUtils.size(poiItems)];
                                CollectionUtils.forAllDo(poiItems, (index, item) -> pois[index] = item.getTitle() + "\n" + "地址：" + item.getSnippet() + "\n" + "距离：" + item.getDistance() + "米" + "\n");
                                showItemsDialog("天安门搜索结果", pois, null);
                                removeProgress();
                            }

                            @Override
                            public void onPoiItemSearched(PoiItem poiItem, int i) {
                            }
                        });
    });

    public BindingCommand startNearPoiPage = new BindingCommand(() ->
            TabMapApi.getChoosingApi().startNearPoiPageForResult(this, poiItem -> showSingleActionDialog("附近POI", poiItem.toString(), "确定", (dialog, index) -> dialog.dismiss())));

    public BindingCommand startPreciseLocationPage = new BindingCommand(() ->
            TabMapApi.getChoosingApi().startPreciseLocationPageForResult(this, locationResult -> showSingleActionDialog("精准位置", locationResult.toString(), "确定", (dialog, index) -> dialog.dismiss())));

}
