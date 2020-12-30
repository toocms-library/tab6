package com.toocms.tab.map.choosing.poi.near;

import android.Manifest;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.binding.ItemBinding;
import com.toocms.tab.binding.command.BindingCommand;
import com.toocms.tab.bus.event.SingleLiveEvent;
import com.toocms.tab.map.BR;
import com.toocms.tab.map.R;
import com.toocms.tab.map.TabMapApi;
import com.toocms.tab.map.choosing.poi.search.ObtainSearchPoiFragment;
import com.toocms.tab.map.location.TooCMSLocationApi;
import com.toocms.tab.map.location.listener.LocationListener;
import com.toocms.tab.map.poi.TooCMSPoiApi;

/**
 * Author：Zero
 * Date：2020/12/15 17:45
 */
public class ObtainNearPoiViewModel extends BaseViewModel implements PermissionUtils.SimpleCallback {

    private static final int REQUEST_CODE = 0x111;
    protected static final double DEFAULT_LONGITUDE = 39.924206;    // 地图默认经度
    protected static final double DEFAULT_LATITUDE = 116.397865;    // 地图默认纬度

    public SingleLiveEvent<LatLng> locationChanged = new SingleLiveEvent<>();
    protected LatLonPoint latLonPoint;   // 经纬度点
    protected String cityCode;    // 城市编码

    public ObservableList<NearPoiItemViewModel> list = new ObservableArrayList<>();
    public ItemBinding<NearPoiItemViewModel> itemBinding = ItemBinding.of(BR.nearPoiItemViewModel, R.layout.item_near_poi);

    public ObtainNearPoiViewModel(@NonNull Application application) {
        super(application);
    }

    public BindingCommand startSearchPoiPageForResult = new BindingCommand(() -> {
        Bundle bundle = new Bundle();
        bundle.putString("cityCode", cityCode);
        startFragmentForResult(ObtainSearchPoiFragment.class, bundle, REQUEST_CODE);
    });

    /**
     * 开始定位
     */
    protected void startLocation() {
        TooCMSLocationApi.getInstance().start(new LocationListener() {
            @Override
            public void onLocationSuccess(AMapLocation aMapLocation) {
                cityCode = aMapLocation.getCityCode();
                LatLng curLatlng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                latLonPoint = new LatLonPoint(curLatlng.latitude, curLatlng.longitude);
                locationChanged.setValue(curLatlng);
            }

            @Override
            public void onLocationFail() {
            }
        });
    }

    public void requestPermissions() {
        PermissionUtils.permission(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .callback(this)
                .request();
    }

    public void doSearch(double latitude, double longitude) {
        TooCMSPoiApi.getInstance().doSearchPoi(
                latitude,
                longitude,
                1000,
                null,
                TabMapApi.DEFAULT_POI_TYPE,
                1,
                40,
                new PoiSearch.OnPoiSearchListener() {
                    @Override
                    public void onPoiSearched(PoiResult poiResult, int i) {
                        list.clear();
                        for (int j = 0; j < CollectionUtils.size(poiResult.getPois()); j++) {
                            NearPoiItemViewModel poiItemViewModel = new NearPoiItemViewModel(ObtainNearPoiViewModel.this, poiResult.getPois().get(j));
                            list.add(poiItemViewModel);
                        }
                    }

                    @Override
                    public void onPoiItemSearched(PoiItem poiItem, int i) {

                    }
                });
    }

    @Override
    public void onGranted() {
        startLocation();
    }

    @Override
    public void onDenied() {
        doSearch(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TabMapApi.release();
    }
}
