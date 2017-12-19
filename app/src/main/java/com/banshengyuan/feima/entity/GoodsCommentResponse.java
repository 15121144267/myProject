package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/8/28.
 * GoodsCommentResponse
 */

public class GoodsCommentResponse implements Serializable {


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
         * id : 4
         * user_id : 1
         * user_name : test444
         * user_head_img :
         * content : 123123123
         */

        public int id;
        public int user_id;
        public String user_name;
        public String user_head_img;
        public String content;
    }
}
