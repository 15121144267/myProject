package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/8/28.
 * SearchResultResponse
 */

public class SearchResultResponse implements Serializable{

    /**
     * list : [{"id":1,"name":"名称","summary":"这是简介","cover_img":"http=>//example.com/1.png"},{"id":1,"name":"名称","summary":"这是简介","cover_img":"http=>//example.com/1.png"},{"id":1,"name":"名称","summary":"这是简介","cover_img":"http=>//example.com/1.png"}]
     * current_page : 1
     * has_next_page : true
     */

    public int current_page;
    public boolean has_next_page;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 1
         * name : 名称
         * summary : 这是简介
         * cover_img : http=>//example.com/1.png
         */

        public int id;
        public String name;
        public String summary;
        public String cover_img;
        public String shop_logo;
        public int price;
        public String category;
    }
}
