package com.banshengyuan.feima.entity;

import java.util.List;

/**
 * Created by lei.he on 2017/12/11.
 */

public class OrderConfirmItem {

    /**
     * store_id : 1
     * store_name : 潮流衣铺
     * product : [{"goods_id":1,"goods_sku":"23123","number":2},{"goods_id":2,"goods_sku":"2312312","number":1}]
     * ticket_id : 12
     * remark : 给商家2的留言
     */

    public int store_id;
    public String store_name;
    public int ticket_id;
    public String remark;
    public List<ProductBean> product;

    public static class ProductBean {
        /**
         * goods_id : 1
         * goods_sku : 23123
         * number : 2
         */

        public int goods_id;
        public String goods_sku;
        public int number;
    }
}
