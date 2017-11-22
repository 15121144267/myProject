package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/11/21.
 */

public class BlockFairListResponse implements Serializable {

    /**
     * list : [{"id":1,"name":"市集分类名字","summary":"简介","cover_img":"http://example.com/1.png","open_time":"周五 19:00-21:00"},{"id":2,"name":"市集分类名字","summary":"简介","cover_img":"http://example.com/1.png","open_time":"周五 19:00-21:00"},{"id":3,"name":"市集分类名字","summary":"简介","cover_img":"http://example.com/1.png","open_time":"周五 19:00-21:00"}]
     * current_page : 1
     * has_next_page : true
     */

    public int current_page;
    public boolean has_next_page;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 1
         * name : 市集分类名字
         * summary : 简介
         * cover_img : http://example.com/1.png
         * open_time : 周五 19:00-21:00
         */

        public int id;
        public String name;
        public String summary;
        public String cover_img;
        public String open_time;
    }
}
