package com.toocms.tab.pay.modle;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * 支付返回签名结果基类
 * Author：Zero
 * Date：2017/3/29 14:37
 */
public class PayRequest {

    // 支付宝
    private String link_string;
    @SerializedName("private")
    private String privateX;

    public String getLink_string() {
        return link_string;
    }

    public void setLink_string(String link_string) {
        this.link_string = link_string;
    }

    public String getPrivate() {
        return privateX;
    }

    public void setPrivate(String privateX) {
        this.privateX = privateX;
    }

    public String showAliRequest() {
        return "AliRequest{" +
                "link_string='" + link_string + '\'' +
                ", private='" + privateX +
                '}';
    }

    // 微信
    private String appid;
    private String partnerid;
    @SerializedName("package")
    private String packageX;
    private String noncestr;
    private int timestamp;
    private String prepayid;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPackage() {
        return packageX;
    }

    public void setPackage(String packageX) {
        this.packageX = packageX;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String showWxRequest() {
        return "WxRequest{" +
                "appid='" + appid + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", package='" + packageX + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", timestamp=" + timestamp +
                ", prepayid='" + prepayid + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    @Override
    public String toString() {
        return TextUtils.isEmpty(getAppid()) ? showAliRequest() : showWxRequest();
    }
}
