package com.toocms.tab.widget.banner;

/**
 * 图片轮播条目
 *
 * @author xuexiang
 * @since 2018/11/25 下午7:01
 */
public class BannerItem {

    public String title;
    public String imgUrl;
    public String target_rule;
    public String param;
    public String expand1;
    public String expand2;
    public String expand3;

    public String getTitle() {
        return title;
    }

    public BannerItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public BannerItem setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public String getTarget_rule() {
        return target_rule;
    }

    public void setTarget_rule(String target_rule) {
        this.target_rule = target_rule;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getExpand1() {
        return expand1;
    }

    public void setExpand1(String expand1) {
        this.expand1 = expand1;
    }

    public String getExpand2() {
        return expand2;
    }

    public void setExpand2(String expand2) {
        this.expand2 = expand2;
    }

    public String getExpand3() {
        return expand3;
    }

    public void setExpand3(String expand3) {
        this.expand3 = expand3;
    }
}
