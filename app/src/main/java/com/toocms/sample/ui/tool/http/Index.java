package com.toocms.sample.ui.tool.http;

import java.io.Serializable;
import java.util.List;

/**
 * Author：Zero
 * Date：2017/3/29 10:52
 */

public class Index implements Serializable {

    /**
     * adverts : []
     * navigation : [{"name":"品质正餐","target_rule":"3","param":"1","icon":"3","icon_path":"http://xlg-file.uuudoo.com/Uploads/Navigation/2019-11-11/5dc9270112b2c.png"}]
     * kill : {}
     * section : [{"name":"配送","layout":"1","configure":[{"cover":"18","target_rule":"","param":"","cover_path":"http://xlg-file.uuudoo.com/Uploads/Section/2020-03-03/5e5dc5b4d8d73.jpg"}]},{"name":"测试1","layout":"1","configure":[{"cover":"2","target_rule":"2","param":"","cover_path":"http://xlg-file.uuudoo.com/Uploads/Config/2019-11-11/5dc8ccce5b0b6.png"}]}]
     * recommend_goods : [{"goods_id":"1","goods_name":"麻辣小龙虾","cover":"2","price":"31.00","member_price":"26.00","main_material":"小龙虾","sales":"217","cover_path":"http://xlg-file.uuudoo.com/Uploads/Config/2019-11-11/5dc8ccce5b0b6.png","is_attr":"1","cart_num":"0"},{"goods_id":"3","goods_name":"红烧土豆124","cover":"17","price":"12.00","member_price":"10.00","main_material":"土豆，红椒","sales":"128","cover_path":"http://xlg-file.uuudoo.com/Uploads/Goods/2020-03-03/5e5dc3e8b0687.jpg","is_attr":"1","cart_num":"0"}]
     * public_notice : 店铺刚刚开业，快来选购
     * msg_count : 0
     */

    private KillBean kill;
    private String public_notice;
    private String msg_count;
    private List<NavigationBean> navigation;
    private List<SectionBean> section;
    private List<RecommendGoodsBean> recommend_goods;

    @Override
    public String toString() {
        return "Index{" +
                "kill=" + kill +
                ", public_notice='" + public_notice + '\'' +
                ", msg_count='" + msg_count + '\'' +
                ", navigation=" + navigation +
                ", section=" + section +
                ", recommend_goods=" + recommend_goods +
                '}';
    }

    public KillBean getKill() {
        return kill;
    }

    public void setKill(KillBean kill) {
        this.kill = kill;
    }

    public String getPublic_notice() {
        return public_notice;
    }

    public void setPublic_notice(String public_notice) {
        this.public_notice = public_notice;
    }

    public String getMsg_count() {
        return msg_count;
    }

    public void setMsg_count(String msg_count) {
        this.msg_count = msg_count;
    }

    public List<NavigationBean> getNavigation() {
        return navigation;
    }

    public void setNavigation(List<NavigationBean> navigation) {
        this.navigation = navigation;
    }

    public List<SectionBean> getSection() {
        return section;
    }

    public void setSection(List<SectionBean> section) {
        this.section = section;
    }

    public List<RecommendGoodsBean> getRecommend_goods() {
        return recommend_goods;
    }

    public void setRecommend_goods(List<RecommendGoodsBean> recommend_goods) {
        this.recommend_goods = recommend_goods;
    }

    public static class KillBean {
    }

    public static class NavigationBean {
        /**
         * name : 品质正餐
         * target_rule : 3
         * param : 1
         * icon : 3
         * icon_path : http://xlg-file.uuudoo.com/Uploads/Navigation/2019-11-11/5dc9270112b2c.png
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

        @Override
        public String toString() {
            return "NavigationBean{" +
                    "name='" + name + '\'' +
                    ", target_rule='" + target_rule + '\'' +
                    ", param='" + param + '\'' +
                    ", icon='" + icon + '\'' +
                    ", icon_path='" + icon_path + '\'' +
                    '}';
        }
    }

    public static class SectionBean {
        /**
         * name : 配送
         * layout : 1
         * configure : [{"cover":"18","target_rule":"","param":"","cover_path":"http://xlg-file.uuudoo.com/Uploads/Section/2020-03-03/5e5dc5b4d8d73.jpg"}]
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
             * cover : 18
             * target_rule :
             * param :
             * cover_path : http://xlg-file.uuudoo.com/Uploads/Section/2020-03-03/5e5dc5b4d8d73.jpg
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

            @Override
            public String toString() {
                return "ConfigureBean{" +
                        "cover='" + cover + '\'' +
                        ", target_rule='" + target_rule + '\'' +
                        ", param='" + param + '\'' +
                        ", cover_path='" + cover_path + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "SectionBean{" +
                    "name='" + name + '\'' +
                    ", layout='" + layout + '\'' +
                    ", configure=" + configure +
                    '}';
        }
    }

    public static class RecommendGoodsBean {
        /**
         * goods_id : 1
         * goods_name : 麻辣小龙虾
         * cover : 2
         * price : 31.00
         * member_price : 26.00
         * main_material : 小龙虾
         * sales : 217
         * cover_path : http://xlg-file.uuudoo.com/Uploads/Config/2019-11-11/5dc8ccce5b0b6.png
         * is_attr : 1
         * cart_num : 0
         */

        private String goods_id;
        private String goods_name;
        private String cover;
        private String price;
        private String member_price;
        private String main_material;
        private String sales;
        private String cover_path;
        private String is_attr;
        private String cart_num;

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

        public String getMember_price() {
            return member_price;
        }

        public void setMember_price(String member_price) {
            this.member_price = member_price;
        }

        public String getMain_material() {
            return main_material;
        }

        public void setMain_material(String main_material) {
            this.main_material = main_material;
        }

        public String getSales() {
            return sales;
        }

        public void setSales(String sales) {
            this.sales = sales;
        }

        public String getCover_path() {
            return cover_path;
        }

        public void setCover_path(String cover_path) {
            this.cover_path = cover_path;
        }

        public String getIs_attr() {
            return is_attr;
        }

        public void setIs_attr(String is_attr) {
            this.is_attr = is_attr;
        }

        public String getCart_num() {
            return cart_num;
        }

        public void setCart_num(String cart_num) {
            this.cart_num = cart_num;
        }

        @Override
        public String toString() {
            return "RecommendGoodsBean{" +
                    "goods_id='" + goods_id + '\'' +
                    ", goods_name='" + goods_name + '\'' +
                    ", cover='" + cover + '\'' +
                    ", price='" + price + '\'' +
                    ", member_price='" + member_price + '\'' +
                    ", main_material='" + main_material + '\'' +
                    ", sales='" + sales + '\'' +
                    ", cover_path='" + cover_path + '\'' +
                    ", is_attr='" + is_attr + '\'' +
                    ", cart_num='" + cart_num + '\'' +
                    '}';
        }
    }
}
