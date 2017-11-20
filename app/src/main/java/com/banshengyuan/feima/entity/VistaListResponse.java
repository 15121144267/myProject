package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/11/20.
 */

public class VistaListResponse implements Serializable {

    /**
     * list : [{"id":1,"name":"街区1","summary":"这是街区的简介","cover_img":"http=>//example.com/1.png","create_time":1510041409},{"id":1,"name":"街区1","summary":"这是街区的简介","cover_img":"http=>//example.com/1.png","create_time":1510041409},{"id":1,"name":"街区1","summary":"这是街区的简介","cover_img":"http=>//example.com/1.png","create_time":1510041409}]
     * current_page : 1
     * has_next_page : true
     */

    public int current_page;
    public boolean has_next_page;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 1
         * name : 街区1
         * summary : 这是街区的简介
         * cover_img : http=>//example.com/1.png
         * create_time : 1510041409
         */

        public int id;
        public String name;
        public String summary;
        public String cover_img;
        public int create_time;
    }
}
