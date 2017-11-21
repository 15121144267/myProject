package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/11/21.
 */

public class AllProductSortResponse implements Serializable {

    /**
     * list : [{"id":1,"name":"分类名字","cover_img":"http=>//example.com/a2.png"},{"id":2,"name":"分类名字","cover_img":"http=>//example.com/a1.png"},{"id":3,"name":"分类名字","cover_img":"http=>//example.com/a3.png"}]
     * current_page : 1
     * has_next_page : true
     */

    public int current_page;
    public boolean has_next_page;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 1
         * name : 分类名字
         * cover_img : http=>//example.com/a2.png
         */

        public int id;
        public String name;
        public String cover_img;
    }
}
