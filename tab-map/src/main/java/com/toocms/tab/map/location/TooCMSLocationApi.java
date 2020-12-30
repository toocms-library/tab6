package com.toocms.tab.map.location;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.toocms.tab.map.location.listener.LocationListener;
import com.toocms.tab.map.location.listener.NearestBuildingListener;

/**
 * 定位类
 * <p>
 * 基于高德地图实现
 * <p>
 * Author：Zero
 * Date：2020年6月9日
 */
public class TooCMSLocationApi {

    /**
     * 默认的定位选项
     */
    private final AMapLocationClientOption DEFAULT_OPTION = new AMapLocationClientOption()
            .setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy)  // 高精度模式
            .setOnceLocation(true)    // 获取一次定位结果
            .setNeedAddress(true);  // 返回地址信息

    private AMapLocationClient client;
    private AMapLocationClientOption option = DEFAULT_OPTION;
    private LocationListener locationListener;

    private NearestBuildingListener nearestBuildingListener;

    private boolean isSearchNearestBuilding; // 是否需要距离最近的建筑物

    /**
     * 内部定位回调监听
     */
    private AMapLocationListener internalListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation == null) return;
            int errorCode = aMapLocation.getErrorCode();
            Log.e("TabMap", ErrorCode.errorCode.get(errorCode));
            if (errorCode == 0) { // 定位成功
                // 获取距离最近建筑物
                if (isSearchNearestBuilding) {
                    String aoiName = aMapLocation.getAoiName();
                    Building building = new Building(
                            aMapLocation.getProvince(),
                            aMapLocation.getCity(),
                            aMapLocation.getDistrict(),
                            TextUtils.isEmpty(aoiName) ? aMapLocation.getPoiName() : aoiName,
                            aMapLocation.getLatitude(),
                            aMapLocation.getLongitude());
                    if (nearestBuildingListener != null)
                        nearestBuildingListener.onSearchNearestBuilding(building);
                }
                // 自定义回调监听
                if (locationListener != null) locationListener.onLocationSuccess(aMapLocation);
            } else { // 定位失败
                if (locationListener != null) locationListener.onLocationFail();
            }
        }
    };

    private volatile static TooCMSLocationApi instance;

    private TooCMSLocationApi() {
        client = new AMapLocationClient(ActivityUtils.getTopActivity());
    }

    public static TooCMSLocationApi getInstance() {
        if (instance == null)
            synchronized (TooCMSLocationApi.class) {
                if (instance == null)
                    instance = new TooCMSLocationApi();
            }
        return instance;
    }

    /**
     * 设置定位选项
     *
     * @param option
     */
    public TooCMSLocationApi setLocationOption(AMapLocationClientOption option) {
        this.option = option;
        return this;
    }

    /**
     * 开始定位
     *
     * @param locationListener
     */
    public void start(@NonNull LocationListener locationListener) {
        this.locationListener = locationListener;
        // 设置定位选项
        client.setLocationOption(option);
        // 设置监听
        client.setLocationListener(internalListener);
        // 开始定位
        client.startLocation();
    }

    /**
     * 通过定位获取距离最近的建筑物
     *
     * @param nearestBuildingListener 获取到距离最近的建筑物的回调监听
     */
    public void start(@NonNull NearestBuildingListener nearestBuildingListener) {
        isSearchNearestBuilding = true;
        this.nearestBuildingListener = nearestBuildingListener;
        // 设置定位选项
        client.setLocationOption(option);
        // 设置监听
        client.setLocationListener(internalListener);
        // 开始定位
        client.startLocation();
    }

    /**
     * 停止定位
     */
    public void stop() {
        if (client != null) client.stopLocation();
    }

    /**
     * 释放
     */
    public void release() {
        internalListener = null;
        locationListener = null;
        if (client != null) {
            client.stopLocation();
            client.onDestroy();
        }
        client = null;
        instance = null;
    }
}
