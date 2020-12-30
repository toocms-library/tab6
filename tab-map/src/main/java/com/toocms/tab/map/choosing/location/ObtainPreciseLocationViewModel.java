package com.toocms.tab.map.choosing.location;

import android.Manifest;
import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.binding.command.BindingCommand;
import com.toocms.tab.bus.Messenger;
import com.toocms.tab.bus.event.SingleLiveEvent;
import com.toocms.tab.map.TabMapApi;
import com.toocms.tab.map.choosing.TooCMSChoosingApi;
import com.toocms.tab.map.location.listener.LocationListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Author：Zero
 * Date：2020/12/15 14:41
 */
public class ObtainPreciseLocationViewModel extends BaseViewModel implements PermissionUtils.SimpleCallback {

    protected static final int DEFAULT_RANGE = 1000;    // 默认搜索范围，单位：米
    protected static final double DEFAULT_LONGITUDE = 39.924206;    // 地图默认经度
    protected static final double DEFAULT_LATITUDE = 116.397865;    // 地图默认纬度

    GeocodeSearch geocodeSearch;  // 逆地理编码查询

    public DecimalFormat decimalFormat = new DecimalFormat("#.######");
    public LocationResult result = new LocationResult();  // 定位结果

    public SingleLiveEvent<LatLng> locationChanged = new SingleLiveEvent<>();

    protected LatLonPoint latLonPoint;   // 经纬度点
    protected String cityCode;    // 城市编码
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> address = new ObservableField<>();
    public ObservableBoolean hasAddress = new ObservableBoolean();

    public ObtainPreciseLocationViewModel(@NonNull Application application) {
        super(application);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);    // 经纬度四舍五入
        // 初始化地理编码类
        geocodeSearch = new GeocodeSearch(application);
    }

    public BindingCommand sure = new BindingCommand(() -> {
        finishFragment();
        Messenger.getDefault().send(result, TooCMSChoosingApi.MESSENGER_TOKEN_PRECISE_LOCATION);
    });

    @Override
    public void onStart() {
        super.onStart();
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int resultID) {
                if (resultID == AMapException.CODE_AMAP_SUCCESS) {
                    if (regeocodeResult == null) return;
                    RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
                    if (regeocodeAddress == null) return;
                    // 若有poi返回则显示poi名称和地址，否则返回乡镇名称和地址
                    List<PoiItem> poiItems = regeocodeAddress.getPois();    // poi
                    String township = regeocodeAddress.getTownship();   // 乡镇名称
                    String province = regeocodeAddress.getProvince();
                    String city = regeocodeAddress.getCity();
                    String district = regeocodeAddress.getDistrict();
                    String provinceCode = "";
                    String cityCode = regeocodeAddress.getCityCode();
                    String districtCode = regeocodeAddress.getAdCode();

                    if (!CollectionUtils.isEmpty(poiItems)) {
                        PoiItem poiItem = poiItems.get(0);
                        name.set(poiItem.getTitle());
                        address.set(poiItem.getSnippet());
                    } else if (!TextUtils.isEmpty(township)) {
                        name.set(township);
                        if (TextUtils.equals(province, city)) {
                            address.set(city + district + township);
                        } else {
                            address.set(province + city + district + township);
                        }
                    } else {
                        name.set(regeocodeAddress.getFormatAddress());
                    }
                    // 什么都没有直接返回
                    if (TextUtils.isEmpty(name.get()) && TextUtils.isEmpty(address.get())) return;
                    // 名字/地址有一个为空则隐藏地址只显示名称
                    hasAddress.set(TextUtils.isEmpty(name.get()) || TextUtils.isEmpty(address.get()));
                    // 回调赋值
                    result.setName(name.get());
                    result.setAddress(address.get());
                    result.setProvince(province);
                    result.setProvinceCode(provinceCode);
                    result.setCity(city);
                    result.setCityCode(cityCode);
                    result.setDistrict(district);
                    result.setDistrictCode(districtCode);
                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int resultID) {
            }
        });
    }

    /**
     * 开始定位
     */
    protected void startLocation() {
        TabMapApi.getLocationApi().start(new LocationListener() {
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

    public void doSearch(double longitude, double latitude) {
        latLonPoint = new LatLonPoint(longitude, latitude);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, DEFAULT_RANGE, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);
    }

    @Override
    public void onGranted() {
        startLocation();
    }

    @Override
    public void onDenied() {
        doSearch(DEFAULT_LONGITUDE, DEFAULT_LATITUDE);
    }
}
