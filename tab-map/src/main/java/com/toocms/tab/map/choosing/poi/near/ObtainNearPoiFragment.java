package com.toocms.tab.map.choosing.poi.near;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.blankj.utilcode.util.ConvertUtils;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.toocms.tab.base.BaseFragment;
import com.toocms.tab.map.BR;
import com.toocms.tab.map.R;
import com.toocms.tab.map.TabMapApi;
import com.toocms.tab.map.databinding.FgtObtainNearPoiBinding;
import com.toocms.tab.map.utils.LocationUtils;

import org.jetbrains.annotations.NotNull;

/**
 * Author：Zero
 * Date：2020/12/15 17:44
 */
public class ObtainNearPoiFragment extends BaseFragment<FgtObtainNearPoiBinding, ObtainNearPoiViewModel> {

    private AMap aMap;  // 地图

    private float mapZoomLevel; // 地图缩放等级
    private boolean isFirstLocation = true; // 是否为第一次定位，用于区分是否显示地图移动动画

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.obtainNearPoiMap.onCreate(savedInstanceState);
    }

    @Override
    protected void onFragmentCreated() {
        topBar.setTitle("选择地址");
        // 获取地图缩放等级
        mapZoomLevel = QMUIResHelper.getAttrFloatValue(getBaseFragmentActivity(), R.attr.app_map_zoom_level);
        Drawable drawable = QMUIResHelper.getAttrDrawable(getBaseFragmentActivity(), R.attr.app_map_near_poi_flag_drawable_id);
        if (drawable != null)
            binding.obtainNearPoiSearch.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        // 初始化地图控制器
        aMap = binding.obtainNearPoiMap.getMap();
        aMap.getUiSettings().setCompassEnabled(true);   // 设置指南针显示
        aMap.getUiSettings().setZoomControlsEnabled(false); // 设置缩放控制显示
        aMap.getUiSettings().setMyLocationButtonEnabled(true);  // 设置定位按钮显示
        // 定位按钮点击
        aMap.setLocationSource(new LocationSource() {
            @Override
            public void activate(OnLocationChangedListener onLocationChangedListener) {
                viewModel.requestPermissions();
            }

            @Override
            public void deactivate() {
                TabMapApi.release();
            }
        });
        // 地图加载完毕监听
        aMap.setOnMapLoadedListener(() -> {
            addMarkerInMapCenter();
            // 检查位置服务是否打开
            inspectLocationService();
        });
        // 地图可视区域改变监听
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                viewModel.doSearch(cameraPosition.target.latitude, cameraPosition.target.longitude);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.obtainNearPoiMap.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.obtainNearPoiMap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.obtainNearPoiMap.onPause();
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        binding.obtainNearPoiMap.onSaveInstanceState(outState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_obtain_near_poi;
    }

    @Override
    public int getVariableId() {
        return BR.obtainNearPoiViewModel;
    }

    @Override
    protected void viewObserver() {
        viewModel.locationChanged.observe(this, latLng -> {
            if (isFirstLocation) {
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, mapZoomLevel));
                isFirstLocation = false;
            } else {
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, mapZoomLevel), 500, null);
            }
        });
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        popBackStackAfterResume();
    }

    /**
     * 添加大头针到地图中心位置
     */
    private void addMarkerInMapCenter() {
        LatLng latLng = aMap.getCameraPosition().target;
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
        MarkerOptions options = new MarkerOptions();
        // 设置marker锚点在最底部
        options.anchor(0.5f, 1f);
        // 设置marker图标
        Drawable drawable = QMUIResHelper.getAttrDrawable(getBaseFragmentActivity(), R.attr.app_map_marker_drawable_id);
        if (drawable == null) {
            options.icon(BitmapDescriptorFactory.defaultMarker());
        } else {
            options.icon(BitmapDescriptorFactory.fromBitmap(ConvertUtils.drawable2Bitmap(drawable)));
        }
        // 添加marker到地图
        Marker marker = aMap.addMarker(options);
        // 设置marker在屏幕上，不跟随地图移动
        marker.setPositionByPixels(screenPosition.x, screenPosition.y);
        marker.setZIndex(0);
    }

    /**
     * 检查是否开启位置服务
     * 打开 → 定位
     * 未打开 → 提示去打开
     */
    public void inspectLocationService() {
        if (!LocationUtils.isLocationEnabled())
            LocationUtils.toOpenGPS(this);
        else {
            viewModel.requestPermissions();
        }
    }
}
