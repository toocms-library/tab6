package com.toocms.sample.ui.widget.banner;

import java.util.List;

/**
 * Author：Zero
 * Date：2020/11/2 11:12
 */
public class Banner {

    private List<AdvertsBean> adverts;

    public List<AdvertsBean> getAdverts() {
        return adverts;
    }

    public void setAdverts(List<AdvertsBean> adverts) {
        this.adverts = adverts;
    }

    public static class AdvertsBean {

        private String ad_id;
        private String target_rule;
        private String param;
        private String abs_url;

        public String getAd_id() {
            return ad_id;
        }

        public void setAd_id(String ad_id) {
            this.ad_id = ad_id;
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

        public String getAbs_url() {
            return abs_url;
        }

        public void setAbs_url(String abs_url) {
            this.abs_url = abs_url;
        }
    }
}
