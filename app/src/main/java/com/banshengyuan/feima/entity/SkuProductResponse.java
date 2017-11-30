package com.banshengyuan.feima.entity;

import java.io.Serializable;

/**
 * Created by lei.he on 2017/7/14.
 * SpecificationResponse
 */

public class SkuProductResponse implements Serializable {

    /**
     * info : {"sku":"0001","name":"测试商品","img":"/upload/example.jpg","price":"100.00","stock":0,"freight":0}
     */

    public InfoBean info;

    public static class InfoBean {
        /**
         * sku : 0001
         * name : 测试商品
         * img : /upload/example.jpg
         * price : 100.00
         * stock : 0
         * freight : 0
         */

        public String sku;
        public String name;
        public String img;
        public int price;
        public int stock;
        public int freight;
    }
}
