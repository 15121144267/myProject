package com.banshengyuan.feima.entity;

import java.util.List;

/**
 * Created by li.liu on 2017/11/21.
 * 热闹- 列表 响应数据
 */

public class ExChangeResponse {


    /**
     * list : [{"id":1,"name":"热闹1名字","cover_img":"http=>//example.com/a1.png","start_time":"1510036420","end_time":"1520036420","summary":"热闹1的简介","street_id":1,"street_name":"街区名字"},{"id":1,"name":"热闹1名字","cover_img":"http=>//example.com/a1.png","start_time":"1510036420","end_time":"1520036420","summary":"热闹1的简介","street_id":1,"street_name":"街区名字"},{"id":1,"name":"热闹1名字","cover_img":"http=>//example.com/a1.png","start_time":"1510036420","end_time":"1520036420","summary":"热闹1的简介","street_id":1,"street_name":"街区名字"}]
     * current_page : 1
     * has_next_page : true
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
         * name : 热闹1名字
         * cover_img : http=>//example.com/a1.png
         * start_time : 1510036420
         * end_time : 1520036420
         * summary : 热闹1的简介
         * street_id : 1
         * street_name : 街区名字
         */

        private int id;
        private String name;
        private String cover_img;
        private String start_time;
        private String end_time;
        private String summary;
        private String street_id;
        private String street_name;

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

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getStreet_id() {
            return street_id;
        }

        public void setStreet_id(String street_id) {
            this.street_id = street_id;
        }

        public String getStreet_name() {
            return street_name;
        }

        public void setStreet_name(String street_name) {
            this.street_name = street_name;
        }
    }
}
