package com.banshengyuan.feima.entity;

import java.util.List;

/**
 * 我的收藏-街区
 */

public class MyCollectionBlockResponse {
    /**
     * list : [{"id":1,"name":"","cover_img":"","summary":""}]
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
         * id : 1
         * name :
         * cover_img :
         * summary :
         */

        private int id;
        private String name;
        private String cover_img;
        private String summary;

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

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }
    }
}
