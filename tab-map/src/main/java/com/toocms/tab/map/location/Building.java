package com.toocms.tab.map.location;

/**
 * 建筑物实体类
 * <p>
 * Author：Zero
 * Date：2020/6/9 17:46
 *
 * @version v1.0
 */

public class Building {

    private String province;
    private String city;
    private String district;

    /**
     * AOI&POI，优先AOI
     */
    private String name;
    private double latitude;
    private double longitude;

    public Building(String province, String city, String district, String name, double latitude, double longitude) {
        this.province = province;
        this.city = city;
        this.district = district;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * 获取省名称
     *
     * @return
     */
    public String getProvince() {
        return province;
    }

    /**
     * 获取市名称
     *
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     * 获取区/县名称
     *
     * @return
     */
    public String getDistrict() {
        return district;
    }

    /**
     * 获取建筑物名称，AOI/POI
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 获取纬度
     *
     * @return
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * 获取经度
     *
     * @return
     */
    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Building{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
