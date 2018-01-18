package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/11/21.
 */

public class BlockHotListResponse implements Serializable {

    /**
     * list : [{"id":1,"name":"热闹1名字","cover_img":"http=>//example.com/a1.png","start_time":"1510036420","end_time":"1520036420","summary":"热闹1的简介","street_id":1,"street_name":"街区名字"},{"id":1,"name":"热闹1名字","cover_img":"http=>//example.com/a1.png","start_time":"1510036420","end_time":"1520036420","summary":"热闹1的简介","street_id":1,"street_name":"街区名字"},{"id":1,"name":"热闹1名字","cover_img":"http=>//example.com/a1.png","start_time":"1510036420","end_time":"1520036420","summary":"热闹1的简介","street_id":1,"street_name":"街区名字"}]
     * current_page : 1
     * has_next_page : true
     */

    public int current_page;
    public boolean has_next_page;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 1
         * name : 热闹1名字
         * cover_img : http=>//example.com/a1.png
         * start_time : 1510036420
         * end_time : 1520036420
         * summary : 热闹1的简介
         * street_id : 1
         * street_name : 街区名字
         * status
         */

        public int id;
        public String name;
        public String cover_img;
        public String start_time;
        public String end_time;
        public String summary;
        public int street_id;
        public String street_name;
        public int status;
    }
}
