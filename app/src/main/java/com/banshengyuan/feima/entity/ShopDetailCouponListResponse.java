package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/11/23.
 */

public class ShopDetailCouponListResponse implements Serializable{

    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 13
         * name : 20171201满99减50
         * type : 1
         * start_time : 2017-12-01 00:00:00
         * end_time : 2017-12-31 00:00:00
         * start_val : 99
         * end_val : 50
         * price : 50
         * is_received : true
         */

        public int id;
        public String name;
        public int type;
        public String start_time;
        public String end_time;
        public double start_val;
        public double end_val;
        public double price;
        public int is_received;
    }
}
