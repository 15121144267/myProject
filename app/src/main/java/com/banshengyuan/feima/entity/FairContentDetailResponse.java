package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by helei on 2017/5/3.
 * FairContentDetailResponse
 */

public class FairContentDetailResponse implements Serializable {
    /**
     * info : {"id":1,"name":"市集名字","cover_img":"http://example.com/a2.png","create_time":"1510036420","read_count":199,"comment_count":5,"collected_count":3,"zan_count":3,"street_id":1,"street_name":"街区名字","category_1_id":"一级分类id","category_2_id":"二级分类id","category_1_name":"一级分类名","category_2_name":"二级分类名"}
     * detail : [{"content":"第1段图文详情内容","product":[{"id":1,"name":"产品名字1","cover_img":"http://example.com/a2.png"},{"id":2,"name":"产品名字2","cover_img":"http://example.com/a1.png"},{"id":3,"name":"产品名字3","cover_img":"http://example.com/a3.png"}]},{"content":"第2段图文详情内容","product":[{"id":11,"name":"产品名字","cover_img":"http://example.com/a2.png"},{"id":12,"name":"产品名字","cover_img":"http://example.com/a1.png"},{"id":13,"name":"产品名字","cover_img":"http://example.com/a3.png"}]}]
     */

    public InfoBean info;
    public List<DetailBean> detail;

    public static class InfoBean {
        /**
         * id : 1
         * name : 市集名字
         * cover_img : http://example.com/a2.png
         * create_time : 1510036420
         * read_count : 199
         * comment_count : 5
         * collected_count : 3
         * zan_count : 3
         * street_id : 1
         * street_name : 街区名字
         * category_1_id : 一级分类id
         * category_2_id : 二级分类id
         * category_1_name : 一级分类名
         * category_2_name : 二级分类名
         */

        public int id;
        public String name;
        public String cover_img;
        public String create_time;
        public int read_count;
        public int comment_count;
        public int collected_count;
        public int zan_count;
        public int street_id;
        public int is_collection;
        public int is_zan;
        public String street_name;
        public String category_1_id;
        public String category_2_id;
        public String category_1_name;
        public String category_2_name;
    }

    public static class DetailBean {
        /**
         * content : 第1段图文详情内容
         * product : [{"id":1,"name":"产品名字1","cover_img":"http://example.com/a2.png"},{"id":2,"name":"产品名字2","cover_img":"http://example.com/a1.png"},{"id":3,"name":"产品名字3","cover_img":"http://example.com/a3.png"}]
         */

        public String content;
        public List<ProductBean> product;

        public static class ProductBean {
            /**
             * id : 1
             * name : 产品名字1
             * cover_img : http://example.com/a2.png
             */
            public boolean isCollection;
            public int id;
            public String name;
            public String image;
            public String goods_sku;
        }
    }

}
