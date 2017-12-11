package com.banshengyuan.feima.entity;

import java.util.List;

/**
 * Created by li.liu on 2017/12/11.
 */

public class NoticeResponse {

    /**
     * list : [{"id":1,"type":"6","title":"您有一笔待发货的订单","desc":"您的客户正在催您发货，请及时处理。","order_sn":"1","create_time":1511706416},{"id":2,"type":"6","title":"您有一笔待发货的订单","desc":"您的客户正在催您发货，请及时处理。","order_sn":"1","create_time":1511706417}]
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
         * type : 6
         * title : 您有一笔待发货的订单
         * desc : 您的客户正在催您发货，请及时处理。
         * order_sn : 1
         * create_time : 1511706416
         */

        private int id;
        private String type;
        private String title;
        private String desc;
        private String order_sn;
        private int create_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }
    }
}
