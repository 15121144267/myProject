package com.banshengyuan.feima.entity;

import java.util.List;

/**
 * 我的收藏
 */

public class MyCollectionFairResponse {

    /**
     * list : [{"id":1,"name":"测试市集文章1","cover_img":"/uploads/5a24f218f014747412.jpg","created_at":1512370725},{"id":2,"name":"测试市集文章2","cover_img":"/uploads/5a24f2394562470804.jpg","created_at":1512370757},{"id":3,"name":"测试市集文章3","cover_img":"/uploads/5a24f251eb28d53508.jpg","created_at":1512370780}]
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
         * name : 测试市集文章1
         * cover_img : /uploads/5a24f218f014747412.jpg
         * created_at : 1512370725
         */

        private int id;
        private String name;
        private String cover_img;
        private int created_at;

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

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }
    }
}
