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
         * stoer_name :
         * list : [{"id":1,"goods_id":1,"goods_sku":"0003","goods_name":"测试商品","goods_img":"http://ssapp.jixuanjk.com/upload/example.jpg","goods_price":12000,"number":4}]
         */

        public int store_id;
        public int freight;
        public int freightWay;
        public String stoer_name;
        public List<ListBean> list;
        public boolean checkFlag;
        public static class ListBean implements Serializable{
            /**
             * id : 1
             * goods_id : 1
             * goods_sku : 0003
             * goods_name : 测试商品
             * goods_img : http://ssapp.jixuanjk.com/upload/example.jpg
             * goods_price : 12000
             * number : 4
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
