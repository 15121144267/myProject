package com.banshengyuan.feima.dagger.module;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/8/9.
 * ShoppingCardListResponse
 */

public class ShoppingCardListResponse implements Serializable {
    public List<ListBeanX> list;

    public static class ListBeanX implements Serializable{
        /**
         * store_id : 1
         * stoer_name : 店铺名字
         * user_ticket : [{"id":11,"name":"满200立减20","expire_start_time":"1509979057","expire_end_time":"1529979057","type":1,"value":"20000,2000","store_name":"店铺名","store_id":1,"status":1},{"id":12,"name":"全场7折","expire_start_time":"1509979057","expire_end_time":"1529979057","type":2,"value":"0.7","store_name":"店铺名","store_id":1,"status":1}]
         * shop_freight_config : {"freight":1,"free_shipping_price":"99"}
         * list : [{"id":1,"goods_id":1,"goods_sku":"123121","goods_name":"商品名","goods_img":"http://example.com/a2.png","goods_price":"2100","number":1},{"id":2,"goods_id":2,"goods_sku":"123123","goods_name":"商品名","goods_img":"http://example.com/a2.png","goods_price":"2100","number":1},{"id":3,"goods_id":1,"goods_sku":"123124","goods_name":"商品名","goods_img":"http://example.com/a2.png","goods_price":"1100","number":1}]
         */

        public int store_id;
        public String stoer_name;
        public ShopFreightConfigBean shop_freight_config;
        public List<UserTicketBean> user_ticket;
        public List<ListBean> list;
        public boolean checkFlag;
        public int freightWay;
        public static class ShopFreightConfigBean implements Serializable{
            /**
             * freight : 1
             * free_shipping_price : 99
             */

            public int freight;
            public int free_shipping_price;
            public int shipping_price;
        }

        public static class UserTicketBean implements Serializable{
            /**
             * id : 11
             * name : 满200立减20
             * expire_start_time : 1509979057
             * expire_end_time : 1529979057
             * type : 1
             * value : 20000,2000
             * store_name : 店铺名
             * store_id : 1
             * status : 1
             */

            public int id;
            public String name;
            public int expire_start_time;
            public int expire_end_time;
            public int type;
            public double start_val;
            public double end_val;
            public String store_name;
            public int store_id;
            public int status;
        }

        public static class ListBean implements Serializable{
            /**
             * id : 1
             * goods_id : 1
             * goods_sku : 123121
             * goods_name : 商品名
             * goods_img : http://example.com/a2.png
             * goods_price : 2100
             * number : 1
             */

            public int id;
            public int goods_id;
            public String goods_sku;
            public String goods_name;
            public String goods_img;
            public int goods_price;
            public int number;
            public boolean childEditFlag;
            public boolean childCheckFlag;
        }
    }
}
