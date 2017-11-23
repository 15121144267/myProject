package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/11/23.
 */

public class ShopDetailCouponListResponse implements Serializable{

    /**
     * list : [{"id":11,"name":"20元优惠券","type":1,"value":"200,20","is_received":false},{"id":11,"name":"20元优惠券","type":1,"value":"200,20","is_received":false}]
     * current_page : 1
     * has_next_page : true
     */

    public int current_page;
    public boolean has_next_page;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 11
         * name : 20元优惠券
         * type : 1
         * value : 200,20
         * is_received : false
         */

        public int id;
        public String name;
        public int type;
        public String value;
        public boolean is_received;
    }
}
