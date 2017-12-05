package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/12/5.
 */

public class StoreCategoryListResponse implements Serializable {

    /**
     * list : [{"id":10,"name":"test003","cover_img":"","category":"餐饮美食"},{"id":12,"name":"test005","cover_img":"http://ssapp.jixuanjk.com/uploads/5a199a18593c070159.jpg","category":"餐饮美食"}]
     * current_page : 1
     * has_next_page : false
     */

    public int current_page;
    public boolean has_next_page;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 10
         * name : test003
         * cover_img :
         * category : 餐饮美食
         */

        public int id;
        public String name;
        public String cover_img;
        public String category;
    }
}
