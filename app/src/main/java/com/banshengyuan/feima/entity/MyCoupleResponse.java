package com.banshengyuan.feima.entity;

import java.util.List;

/**
 * Created by li.liu on 2017/12/7.
 */

public class MyCoupleResponse {

    /**
     * list : [{"id":1,"name":"85折","expire_start_time":1511618880,"expire_end_time":1512050884,"status":3,"value":"0,0.85","type":2,"store_id":12,"store_name":"test005"},{"id":2,"name":"20优惠券","expire_start_time":1511618880,"expire_end_time":1512050884,"status":3,"value":"20000,2000","type":1,"store_id":12,"store_name":"test005"},{"id":3,"name":"20优惠券","expire_start_time":1511618880,"expire_end_time":1512050884,"status":3,"value":"20000,2000","type":1,"store_id":12,"store_name":"test005"},{"id":4,"name":"20优惠券","expire_start_time":1511618880,"expire_end_time":1512050884,"status":3,"value":"20000,2000","type":1,"store_id":12,"store_name":"test005"},{"id":5,"name":"20优惠券","expire_start_time":1511618880,"expire_end_time":1512050884,"status":3,"value":"20000,2000","type":1,"store_id":12,"store_name":"test005"},{"id":6,"name":"20优惠券","expire_start_time":1511618880,"expire_end_time":1512050884,"status":3,"value":"20000,2000","type":1,"store_id":12,"store_name":"test005"}]
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
         * name : 85折
         * expire_start_time : 1511618880
         * expire_end_time : 1512050884
         * status : 3
         * value : 0,0.85
         * type : 2
         * store_id : 12
         * store_name : test005
         */

        private int id;
        private String name;
        private int expire_start_time;
        private int expire_end_time;
        private int status;
        private String value;
        private int type;
        private int store_id;
        private String store_name;

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

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
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
    }
}
