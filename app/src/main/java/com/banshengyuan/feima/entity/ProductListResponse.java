package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/11/21.
 */

public class ProductListResponse implements Serializable {


    public List<CategoryBean> category;

    public static class CategoryBean {
        /**
         * id : 1
         * name : 产品分类名字
         * cover_img : http://example.com/a2.png
         * goods : [{"id":2,"name":"产品名字","cover_img":"http://example.com/a2.png","price":110}]
         */

        public int id;
        public String name;
        public String cover_img;
        public List<GoodsBean> goods;

        public static class GoodsBean {
            /**
             * id : 2
             * name : 产品名字
             * cover_img : http://example.com/a2.png
             * price : 110
             */

            public int id;
            public String name;
            public String cover_img;
            public int price;
        }
    }
}
