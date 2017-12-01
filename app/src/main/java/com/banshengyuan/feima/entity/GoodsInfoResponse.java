package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/7/10.
 * GoodsInfoResponse
 */

public class GoodsInfoResponse implements Serializable {


    /**
     * info : {"id":1,"name":"测试商品","price":10000,"top_img":["/upload/example.jpg","/upload/example2.jpg","/upload/example3.jpg","/upload/example4.jpg"],"content":"这是一个测试商品","is_collected":false,"main_sku":"0001","stock":10,"other_spec":[{"id":1,"name":"颜色","value":[{"id":1,"name":"红色"},{"id":2,"name":"黄色"},{"id":3,"name":"蓝色"}]},{"id":2,"name":"尺码","value":[{"id":4,"name":"L码"},{"id":5,"name":"M码"},{"id":6,"name":"X码"}]}],"bind_spec":[{"sku_name":"0001","spec_id":"1_4","spec_name":"红色_L码","price":10000,"stock":0},{"sku_name":"0002","spec_id":"1_5","spec_name":"红色_M码","price":11000,"stock":0},{"sku_name":"0003","spec_id":"1_6","spec_name":"红色_X码","price":12000,"stock":10},{"sku_name":"0004","spec_id":"2_4","spec_name":"黄色_L码","price":15000,"stock":0},{"sku_name":"0005","spec_id":"2_5","spec_name":"黄色_M码","price":15000,"stock":0},{"sku_name":"0006","spec_id":"2_6","spec_name":"黄色_X码","price":15000,"stock":0},{"sku_name":"0007","spec_id":"3_4","spec_name":"蓝色_L码","price":15000,"stock":0},{"sku_name":"0008","spec_id":"3_5","spec_name":"蓝色_M码","price":15000,"stock":0},{"sku_name":"0009","spec_id":"3_6","spec_name":"蓝色_X码","price":15000,"stock":0}],"freight":{"freight":2},"store":{"id":1,"location":"","name":"","mobile":""}}
     */

    public InfoBean info;

    public static class InfoBean {
        /**
         * id : 1
         * name : 测试商品
         * price : 10000
         * top_img : ["/upload/example.jpg","/upload/example2.jpg","/upload/example3.jpg","/upload/example4.jpg"]
         * content : 这是一个测试商品
         * is_collected : false
         * main_sku : 0001
         * stock : 10
         * other_spec : [{"id":1,"name":"颜色","value":[{"id":1,"name":"红色"},{"id":2,"name":"黄色"},{"id":3,"name":"蓝色"}]},{"id":2,"name":"尺码","value":[{"id":4,"name":"L码"},{"id":5,"name":"M码"},{"id":6,"name":"X码"}]}]
         * bind_spec : [{"sku_name":"0001","spec_id":"1_4","spec_name":"红色_L码","price":10000,"stock":0},{"sku_name":"0002","spec_id":"1_5","spec_name":"红色_M码","price":11000,"stock":0},{"sku_name":"0003","spec_id":"1_6","spec_name":"红色_X码","price":12000,"stock":10},{"sku_name":"0004","spec_id":"2_4","spec_name":"黄色_L码","price":15000,"stock":0},{"sku_name":"0005","spec_id":"2_5","spec_name":"黄色_M码","price":15000,"stock":0},{"sku_name":"0006","spec_id":"2_6","spec_name":"黄色_X码","price":15000,"stock":0},{"sku_name":"0007","spec_id":"3_4","spec_name":"蓝色_L码","price":15000,"stock":0},{"sku_name":"0008","spec_id":"3_5","spec_name":"蓝色_M码","price":15000,"stock":0},{"sku_name":"0009","spec_id":"3_6","spec_name":"蓝色_X码","price":15000,"stock":0}]
         * freight : {"freight":2}
         * store : {"id":1,"location":"","name":"","mobile":""}
         */

        public int id;
        public String name;
        public int price;
        public String content;
        public boolean is_collected;
        public String main_sku;
        public int stock;
        public FreightBean freight;
        public StoreBean store;
        public List<String> top_img;
        public List<OtherSpecBean> other_spec;
        public List<BindSpecBean> bind_spec;

        public static class FreightBean {
            /**
             * freight : 2
             */

            public int freight;
        }

        public static class StoreBean {
            /**
             * id : 1
             * location :
             * name :
             * mobile :
             */

            public int id;
            public String location;
            public String name;
            public String mobile;
        }

        public static class OtherSpecBean {
            /**
             * id : 1
             * name : 颜色
             * value : [{"id":1,"name":"红色"},{"id":2,"name":"黄色"},{"id":3,"name":"蓝色"}]
             */

            public int id;
            public String name;
            public List<ValueBean> value;

            public static class ValueBean {
                /**
                 * id : 1
                 * name : 红色
                 */
                public boolean enableFlag = true;
                public int id;
                public String name;
            }
        }

        public static class BindSpecBean {
            /**
             * sku_name : 0001
             * spec_id : 1_4
             * spec_name : 红色_L码
             * price : 10000
             * stock : 0
             */

            public String sku_name;
            public String spec_id;
            public String spec_name;
            public int price;
            public int stock;
        }
    }
}
