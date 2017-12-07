package com.banshengyuan.feima.entity;

import java.util.List;

/**
 * Created by lei.he on 2017/11/24.
 */

public class CollectionShopResponse {

    /**
     * list : [{"id":8,"name":"test的店铺","cover_img":"/uploads/5a06a4f2608d998553.png","category":""},{"id":11,"name":"test004","cover_img":"","category":""}]
     * current_page : 1
     * has_next_page : false
     */

    private int current_page;
    private boolean has_next_page;
    private List<ListBean> list;

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public boolean isHas_next_page() {
        return has_next_page;
    }

    public void setHas_next_page(boolean has_next_page) {
        this.has_next_page = has_next_page;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 8
         * name : test的店铺
         * cover_img : /uploads/5a06a4f2608d998553.png
         * category :
         */

        private int id;
        private String name;
        private String cover_img;
        private String category;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCover_img() {
            return cover_img;
        }

        public void setCover_img(String cover_img) {
            this.cover_img = cover_img;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }
}
