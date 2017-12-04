package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/12/4.
 */

public class ProductCategoryResponse implements Serializable {

    /**
     * list : [{"id":1,"name":"测试商品","price":10000,"cover_img":"http://ssapp.jixuanjk.com/upload/example.jpg"}]
     * current_page : 1
     * has_next_page : false
     */

    public String current_page;
    public boolean has_next_page;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 1
         * name : 测试商品
         * price : 10000
         * cover_img : http://ssapp.jixuanjk.com/upload/example.jpg
         */

        public int id;
        public String name;
        public int price;
        public String cover_img;
        public String summary;
    }
}
