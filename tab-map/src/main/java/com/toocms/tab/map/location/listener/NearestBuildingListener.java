package com.toocms.tab.map.location.listener;

import com.toocms.tab.map.location.Building;

/**
 * 查询到距离最近建筑物时的回调监听
 * <p>
 * Author：Zero
 * Date：2020年6月10日
 *
 * @version v1.0
 */
public interface NearestBuildingListener {

    /**
     * 查询到距离最新的建筑物
     *
     * @param building 建筑物信息
     */
    void onSearchNearestBuilding(Building building);
}
