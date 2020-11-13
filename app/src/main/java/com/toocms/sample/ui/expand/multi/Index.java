package com.toocms.sample.ui.expand.multi;

import java.util.List;

/**
 * Author：Zero
 * Date：2020/11/6 17:03
 */
public class Index {

    /**
     * adverts : [{"ad_id":"4","target_rule":"1","param":"http://www.toocms.com/","abs_url":"http://hotpotshop-file.uuudoo.com/Uploads/Advert/2020-09-17/5f6325674f4bb.png","shop_id":"0"}]
     * navs : [{"name":"火锅底料","target_rule":"2","param":"5","icon":"43","icon_path":"http://hotpotshop-file.uuudoo.com/Uploads/Navigation/2020-09-17/5f632620479e5.png"},{"name":"火锅蘸料","target_rule":"2","param":"6","icon":"44","icon_path":"http://hotpotshop-file.uuudoo.com/Uploads/Navigation/2020-09-17/5f632661bd140.png"},{"name":"调味料","target_rule":"2","param":"7","icon":"45","icon_path":"http://hotpotshop-file.uuudoo.com/Uploads/Navigation/2020-09-17/5f6326a533dd2.png"},{"name":"自助小火","target_rule":"2","param":"8","icon":"46","icon_path":"http://hotpotshop-file.uuudoo.com/Uploads/Navigation/2020-09-17/5f6326c41c005.png"}]
     * sections : [{"name":"首页板块","layout":"5","configure":[{"cover":"47","target_rule":"2","param":"5","cover_path":"http://hotpotshop-file.uuudoo.com/Uploads/Section/2020-09-17/5f632983419f1.png"},{"cover":"48","target_rule":"2","param":"6","cover_path":"http://hotpotshop-file.uuudoo.com/Uploads/Section/2020-09-17/5f63298debbe2.png"},{"cover":"49","target_rule":"2","param":"7","cover_path":"http://hotpotshop-file.uuudoo.com/Uploads/Section/2020-09-17/5f632992ee60d.png"},{"cover":"50","target_rule":"2","param":"8","cover_path":"http://hotpotshop-file.uuudoo.com/Uploads/Section/2020-09-17/5f63299933714.png"}]},{"name":"一起吃","layout":"1","configure":[{"cover":"51","target_rule":"3","param":"","cover_path":"http://hotpotshop-file.uuudoo.com/Uploads/Section/2020-09-17/5f632b243d765.png"}]},{"name":"火锅底料","layout":"4","configure":[{"cover":"52","target_rule":"2","param":"5","cover_path":"http://hotpotshop-file.uuudoo.com/Uploads/Section/2020-09-17/5f632b6e79f29.png"},{"cover":"53","target_rule":"2","param":"6","cover_path":"http://hotpotshop-file.uuudoo.com/Uploads/Section/2020-09-17/5f632b77d30de.png"},{"cover":"54","target_rule":"2","param":"7","cover_path":"http://hotpotshop-file.uuudoo.com/Uploads/Section/2020-09-17/5f632b7d4d1da.png"},{"cover":"55","target_rule":"2","param":"8","cover_path":"http://hotpotshop-file.uuudoo.com/Uploads/Section/2020-09-17/5f632b8112d62.png"}]}]
     * recommends : [{"goods_id":"1","goods_name":"正品海底捞火锅底料不辣 清汤番 茄菌汤三鲜火锅底料","goods_cate_id":"10","cover":"56","price":"10.00","stock":"392","sales":"24","praise_rate":"100","shop_id":"1","market_price":"15.00","cover_path":"http://hotpotshop-file.uuudoo.com/Uploads/Goods/2020-09-18/5f64104b9d91a.png","shop_name":"李大爷的店"},{"goods_id":"2","goods_name":"小龙坎火锅","goods_cate_id":"8","cover":"64","price":"120.00","stock":"6","sales":"1","praise_rate":"100","shop_id":"2","market_price":"150.00","cover_path":"http://hotpotshop-file.uuudoo.com/Uploads/Shop/2020-09-19/5f65bf20c3e06.png","shop_name":"名称a"}]
     */

    private List<AdvertsBean> adverts;
    private List<NavsBean> navs;
    private List<SectionsBean> sections;
    private List<RecommendsBean> recommends;

    public List<AdvertsBean> getAdverts() {
        return adverts;
    }

    public void setAdverts(List<AdvertsBean> adverts) {
        this.adverts = adverts;
    }

    public List<NavsBean> getNavs() {
        return navs;
    }

    public void setNavs(List<NavsBean> navs) {
        this.navs = navs;
    }

    public List<SectionsBean> getSections() {
        return sections;
    }

    public void setSections(List<SectionsBean> sections) {
        this.sections = sections;
    }

    public List<RecommendsBean> getRecommends() {
        return recommends;
    }

    public void setRecommends(List<RecommendsBean> recommends) {
        this.recommends = recommends;
    }

    public static class AdvertsBean {
        /**
         * ad_id : 4
         * target_rule : 1
         * param : http://www.toocms.com/
         * abs_url : http://hotpotshop-file.uuudoo.com/Uploads/Advert/2020-09-17/5f6325674f4bb.png
         * shop_id : 0
         */

        private String ad_id;
        private String target_rule;
        private String param;
        private String abs_url;
        private String shop_id;

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

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }
    }

    public static class NavsBean {
        /**
         * name : 火锅底料
         * target_rule : 2
         * param : 5
         * icon : 43
         * icon_path : http://hotpotshop-file.uuudoo.com/Uploads/Navigation/2020-09-17/5f632620479e5.png
         */

        private String name;
        private String target_rule;
        private String param;
        private String icon;
        private String icon_path;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIcon_path() {
            return icon_path;
        }

        public void setIcon_path(String icon_path) {
            this.icon_path = icon_path;
        }
    }

    public static class SectionsBean {
        /**
         * name : 首页板块
         * layout : 5
         * configure : [{"cover":"47","target_rule":"2","param":"5","cover_path":"http://hotpotshop-file.uuudoo.com/Uploads/Section/2020-09-17/5f632983419f1.png"},{"cover":"48","target_rule":"2","param":"6","cover_path":"http://hotpotshop-file.uuudoo.com/Uploads/Section/2020-09-17/5f63298debbe2.png"},{"cover":"49","target_rule":"2","param":"7","cover_path":"http://hotpotshop-file.uuudoo.com/Uploads/Section/2020-09-17/5f632992ee60d.png"},{"cover":"50","target_rule":"2","param":"8","cover_path":"http://hotpotshop-file.uuudoo.com/Uploads/Section/2020-09-17/5f63299933714.png"}]
         */

        private String name;
        private String layout;
        private List<ConfigureBean> configure;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLayout() {
            return layout;
        }

        public void setLayout(String layout) {
            this.layout = layout;
        }

        public List<ConfigureBean> getConfigure() {
            return configure;
        }

        public void setConfigure(List<ConfigureBean> configure) {
            this.configure = configure;
        }

        public static class ConfigureBean {
            /**
             * cover : 47
             * target_rule : 2
             * param : 5
             * cover_path : http://hotpotshop-file.uuudoo.com/Uploads/Section/2020-09-17/5f632983419f1.png
             */

            private String cover;
            private String target_rule;
            private String param;
            private String cover_path;

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
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

            public String getCover_path() {
                return cover_path;
            }

            public void setCover_path(String cover_path) {
                this.cover_path = cover_path;
            }
        }
    }

    public static class RecommendsBean {
        /**
         * goods_id : 1
         * goods_name : 正品海底捞火锅底料不辣 清汤番 茄菌汤三鲜火锅底料
         * goods_cate_id : 10
         * cover : 56
         * price : 10.00
         * stock : 392
         * sales : 24
         * praise_rate : 100
         * shop_id : 1
         * market_price : 15.00
         * cover_path : http://hotpotshop-file.uuudoo.com/Uploads/Goods/2020-09-18/5f64104b9d91a.png
         * shop_name : 李大爷的店
         */

        private String goods_id;
        private String goods_name;
        private String goods_cate_id;
        private String cover;
        private String price;
        private String stock;
        private String sales;
        private String praise_rate;
        private String shop_id;
        private String market_price;
        private String cover_path;
        private String shop_name;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_cate_id() {
            return goods_cate_id;
        }

        public void setGoods_cate_id(String goods_cate_id) {
            this.goods_cate_id = goods_cate_id;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getSales() {
            return sales;
        }

        public void setSales(String sales) {
            this.sales = sales;
        }

        public String getPraise_rate() {
            return praise_rate;
        }

        public void setPraise_rate(String praise_rate) {
            this.praise_rate = praise_rate;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getMarket_price() {
            return market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public String getCover_path() {
            return cover_path;
        }

        public void setCover_path(String cover_path) {
            this.cover_path = cover_path;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }
    }
}
