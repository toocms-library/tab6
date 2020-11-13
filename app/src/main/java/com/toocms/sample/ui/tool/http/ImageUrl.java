package com.toocms.sample.ui.tool.http;

import java.util.List;

/**
 * Author：Zero
 * Date：2020/11/2 17:38
 *
 * @version v1.0
 */

public class ImageUrl {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {

        private String id;
        private String abs_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAbs_url() {
            return abs_url;
        }

        public void setAbs_url(String abs_url) {
            this.abs_url = abs_url;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "id='" + id + '\'' +
                    ", abs_url='" + abs_url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ImageUrl{" +
                "list=" + list +
                '}';
    }
}
