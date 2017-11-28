package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/11/28.
 */

public class BlockDetailProductListResponse implements Serializable {

    /**
     * list : [{"id":1,"name":"测试商品","price":"100.00","cover_img":"/upload/example.jpg"},{"id":2,"name":"1211","price":"10.00","cover_img":"/upload/59ff2b2ea1ce281267.jpg"},{"id":3,"name":"第二个","price":"1.00","cover_img":"/upload/59ff2cb5b19a786730.jpg"},{"id":4,"name":"撒旦撒","price":"1.00","cover_img":"/upload/5a00375126d9c41658.jpg"},{"id":5,"name":"121212","price":"0.00","cover_img":"/upload/5a00448bca3d230182.jpg"},{"id":6,"name":"是","price":"1.00","cover_img":"/upload/5a0045793c4d137669.jpg"},{"id":7,"name":"是","price":"1.00","cover_img":"/upload/5a0045793c4d137669.jpg"},{"id":8,"name":"西装","price":"428.00","cover_img":"/upload/5a1bc8ad8636868724.jpg"}]
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
         * price : 100.00
         * cover_img : /upload/example.jpg
         */

        public int id;
        public String name;
        public Integer price;
        public String cover_img;
    }
}
