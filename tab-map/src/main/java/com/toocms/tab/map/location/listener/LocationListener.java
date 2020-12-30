package com.toocms.tab.map.location.listener;

import com.amap.api.location.AMapLocation;

/**
 * 定位监听回调
 * <p>
 * Author：Zero
 * Date：2018/6/13 16:47
 *
 * @version v1.0
 */
public interface LocationListener {

    /**
     * 定位成功回调监听
     */
    void onLocationSuccess(AMapLocation aMapLocation);

    /**
     * 定位失败回调监听
     */
    void onLocationFail();
}
