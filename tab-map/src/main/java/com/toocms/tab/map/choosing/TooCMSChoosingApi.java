package com.toocms.tab.map.choosing;

import com.amap.api.services.core.PoiItem;
import com.toocms.tab.base.BaseFragment;
import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.binding.command.BindingConsumer;
import com.toocms.tab.bus.Messenger;
import com.toocms.tab.map.choosing.location.LocationResult;
import com.toocms.tab.map.choosing.location.ObtainPreciseLocationFragment;
import com.toocms.tab.map.choosing.poi.near.ObtainNearPoiFragment;
import com.toocms.tab.map.location.TooCMSLocationApi;

/**
 * 地址选择Api封装
 * <p>
 * Author：Zero
 * Date：2018/6/23 15:45
 */
public class TooCMSChoosingApi {

    public static final String MESSENGER_TOKEN_NEAR_POI = "MESSENGER_TOKEN_NEAR_POI";
    public static final String MESSENGER_TOKEN_PRECISE_LOCATION = "MESSENGER_TOKEN_PRECISE_LOCATION";

    private volatile static TooCMSChoosingApi instance;

    private TooCMSChoosingApi() {
    }

    public static TooCMSChoosingApi getInstance() {
        if (instance == null)
            synchronized (TooCMSLocationApi.class) {
                if (instance == null)
                    instance = new TooCMSChoosingApi();
            }
        return instance;
    }

    /**
     * 打开精确定位页并接收返回结果
     *
     * @param viewModel 当前页的ViewModel
     * @param consumer  定位结果回调监听
     */
    public void startPreciseLocationPageForResult(BaseViewModel viewModel, BindingConsumer<LocationResult> consumer) {
        Messenger.getDefault().register(viewModel, MESSENGER_TOKEN_PRECISE_LOCATION, LocationResult.class, consumer);
        viewModel.startFragment(ObtainPreciseLocationFragment.class);
    }

    /**
     * 打开附近poi页并接收返回结果
     *
     * @param viewModel 当前页的ViewModel
     * @param consumer  定位结果回调监听
     */
    public void startNearPoiPageForResult(BaseViewModel viewModel, BindingConsumer<PoiItem> consumer) {
        Messenger.getDefault().register(viewModel, MESSENGER_TOKEN_NEAR_POI, PoiItem.class, consumer);
        viewModel.startFragment(ObtainNearPoiFragment.class);
    }

    /**
     * 释放
     */
    public void release() {
        instance = null;
    }
}
