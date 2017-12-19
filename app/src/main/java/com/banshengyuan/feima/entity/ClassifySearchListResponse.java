package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/8/28.
 * ClassifySearchListResponse
 */

public class ClassifySearchListResponse implements Serializable {

    /**
     * list : [{"id":1,"username":"test444","head_img":"","detail":"test","create_time":1511962430},{"id":2,"username":"test444","head_img":"","detail":"test123123","create_time":1511962489},{"id":3,"username":"test444","head_img":"","detail":"12312312","create_time":1511962430}]
     * current_page : 1
     * has_next_page : false
     */

    public int current_page;
    public boolean has_next_page;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 1
         * username : test444
         * head_img :
         * detail : test
         * create_time : 1511962430
         */

        public int id;
        public String username;
        public String head_img;
        public String detail;
        public int create_time;
    }
}
