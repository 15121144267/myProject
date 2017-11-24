package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/7/10.
 * GoodsInfoResponse
 */

public class GoodsInfoResponse implements Serializable {

    /**
     * info : {"id":1,"name":"商品名字","top_img":["http=>//example.com/a1.png","http=>//example.com/a2.png","http=>//example.com/a3.png"],"price":149,"freight":600,"content":"商品图文介绍","is_collected":false,"main_sku":"商品sku码","stock":101,"other_spec":[{"id":10,"name":"颜色","value":[{"id":1,"name":"白色"},{"id":2,"name":"红色"}]},{"id":11,"name":"大小","value":[{"id":3,"name":"16G"},{"id":4,"name":"32G"}]}],"store":{"id":"店铺id","location":"安徽合肥","name":"店铺名字","mobile":"店铺电话"}}
     */

    public InfoBean info;

    public static class InfoBean {
        /**
         * id : 1
         * name : 商品名字
         * top_img : ["http=>//example.com/a1.png","http=>//example.com/a2.png","http=>//example.com/a3.png"]
         * price : 149
         * freight : 600
         * content : 商品图文介绍
         * is_collected : false
         * main_sku : 商品sku码
         * stock : 101
         * other_spec : [{"id":10,"name":"颜色","value":[{"id":1,"name":"白色"},{"id":2,"name":"红色"}]},{"id":11,"name":"大小","value":[{"id":3,"name":"16G"},{"id":4,"name":"32G"}]}]
         * store : {"id":"店铺id","location":"安徽合肥","name":"店铺名字","mobile":"店铺电话"}
         */

        public int id;
        public String name;
        public int price;
        public int freight;
        public String content;
        public boolean is_collected;
        public String main_sku;
        public int stock;
        public StoreBean store;
        public List<String> top_img;
        public List<OtherSpecBean> other_spec;

        public static class StoreBean {
            /**
             * id : 店铺id
             * location : 安徽合肥
             * name : 店铺名字
             * mobile : 店铺电话
             */

            public String id;
            public String location;
            public String name;
            public String mobile;
        }

        public static class OtherSpecBean {
            /**
             * id : 10
             * name : 颜色
             * value : [{"id":1,"name":"白色"},{"id":2,"name":"红色"}]
             */

            public int id;
            public String name;
            public List<ValueBean> value;

            public static class ValueBean {
                /**
                 * id : 1
                 * name : 白色
                 */

                public int id;
                public String name;
            }
        }
    }
}
