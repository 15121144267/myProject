package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by li.liu on 2017/12/7.
 */

public class MyCoupleResponse implements Serializable{


    /**
     * list : [{"id":11,"name":"20171201满99减50","expire_start_time":1512057600,"expire_end_time":1514649600,"status":1,"type":1,"store_id":8,"store_name":"test的店铺","start_val":99,"end_val":50},{"id":12,"name":"20171201满199减100","expire_start_time":1512057600,"expire_end_time":1514649600,"status":1,"type":1,"store_id":8,"store_name":"test的店铺","start_val":199,"end_val":100},{"id":13,"name":"20171201满999减200","expire_start_time":1512057600,"expire_end_time":1514649600,"status":1,"type":1,"store_id":8,"store_name":"test的店铺","start_val":999,"end_val":200},{"id":14,"name":"20171201五折劵","expire_start_time":1512057600,"expire_end_time":1514649600,"status":1,"type":2,"store_id":8,"store_name":"test的店铺","start_val":0,"end_val":0.5}]
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

    public static class ListBean implements Serializable{
        /**
         * id : 11
         * name : 20171201满99减50
         * expire_start_time : 1512057600
         * expire_end_time : 1514649600
         * status : 1
         * type : 1
         * store_id : 8
         * store_name : test的店铺
         * start_val : 99
         * end_val : 50
         */

        private int id;
        private String name;
        private int expire_start_time;
        private int expire_end_time;
        private int status;
        private int type;
        private int store_id;
        private String store_name;
        private double start_val;
        private double end_val;
        public boolean isVisiable;
        public boolean isCheck;
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

        public int getExpire_start_time() {
            return expire_start_time;
        }

        public void setExpire_start_time(int expire_start_time) {
            this.expire_start_time = expire_start_time;
        }

        public int getExpire_end_time() {
            return expire_end_time;
        }

        public void setExpire_end_time(int expire_end_time) {
            this.expire_end_time = expire_end_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public double getStart_val() {
            return start_val;
        }

        public void setStart_val(double start_val) {
            this.start_val = start_val;
        }

        public double getEnd_val() {
            return end_val;
        }

        public void setEnd_val(double end_val) {
            this.end_val = end_val;
        }
    }
}
