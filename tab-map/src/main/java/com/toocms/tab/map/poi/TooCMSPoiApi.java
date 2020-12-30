package com.toocms.tab.map.poi;

import android.content.Context;
import android.text.TextUtils;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.poisearch.PoiSearch;
import com.toocms.tab.TooCMSApplication;
import com.toocms.tab.map.TabMapApi;

/**
 * Author：Zero
 * Date：2020/6/16 17:43
 *
 * @version v1.0
 */

public class TooCMSPoiApi {

    private volatile static TooCMSPoiApi instance;
    private Context context;

    private TooCMSPoiApi() {
        this.context = TooCMSApplication.getInstance();
    }

    public static TooCMSPoiApi getInstance() {
        if (instance == null)
            synchronized (TooCMSPoiApi.class) {
                if (instance == null)
                    instance = new TooCMSPoiApi();
            }
        return instance;
    }

    /**
     * 在指定城市搜索指定关键词相关POI
     *
     * @param keyword       搜索关键词，和搜索类型必传一个
     * @param poiSearchType 搜索类型，{@link TabMapApi#DEFAULT_POI_TYPE}为所有类型/null为不指定，和关键词必传一个
     * @param city          城市编码/名称
     * @param p             页数
     * @param pageSize      每页加载条数
     * @param listener      结果回调监听
     */
    public void doSearchPoi(String keyword, String poiSearchType, String city, int p, int pageSize, PoiSearch.OnPoiSearchListener listener) {
        doSearchPoi(null, keyword, poiSearchType, city, p, pageSize, listener);
    }

    /**
     * 搜索给定坐标点附近一定范围内的POI
     *
     * @param latitude      维度
     * @param longitude     经度
     * @param radius        搜索半径
     * @param keyword       搜索关键词，和搜索类型必传一个
     * @param poiSearchType 搜索类型，{@link TabMapApi#DEFAULT_POI_TYPE}为所有类型/null为不指定，和关键词必传一个
     * @param p             页数
     * @param pageSize      每页加载条数
     * @param listener      结果回调监听
     */
    public void doSearchPoi(double latitude, double longitude, int radius, String keyword, String poiSearchType, int p, int pageSize, PoiSearch.OnPoiSearchListener listener) {
        LatLonPoint point = new LatLonPoint(latitude, longitude);
        PoiSearch.SearchBound searchBound = new PoiSearch.SearchBound(point, radius, true);
        doSearchPoi(searchBound, keyword, poiSearchType, null, p, pageSize, listener);
    }

    /**
     * 搜索指定城市的POI
     *
     * @param searchBound
     * @param keyword       搜索关键词，和搜索类型必传一个
     * @param poiSearchType 搜索类型，{@link TabMapApi#DEFAULT_POI_TYPE}为所有类型/null为不指定，和关键词必传一个
     * @param city          城市编码/名称
     * @param p             页数
     * @param pageSize      每页加载条数
     * @param listener      结果回调监听
     */
    public void doSearchPoi(PoiSearch.SearchBound searchBound, String keyword, String poiSearchType, String city, int p, int pageSize, PoiSearch.OnPoiSearchListener listener) {
        PoiSearch.Query query;
        if (TextUtils.isEmpty(city)) {
            query = new PoiSearch.Query(keyword, poiSearchType);
        } else {
            query = new PoiSearch.Query(keyword, poiSearchType, city);
        }
        query.setPageSize(pageSize);
        query.setPageNum(p);
        PoiSearch search = new PoiSearch(context, query);
        search.setOnPoiSearchListener(listener);
        if (searchBound != null) search.setBound(searchBound);
        search.searchPOIAsyn();
    }

    /**
     * 释放
     */
    public void release() {
        instance = null;
    }
}
