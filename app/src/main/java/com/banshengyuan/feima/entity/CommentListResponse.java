package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/11/22.
 */

public class CommentListResponse implements Serializable {

    /**
     * list : [{"id":1,"username":"用户名","head_img":"http=>//example.com/a2.png","detail":"内容","create_time":"2017-11-13 23=>33=>00"},{"id":1,"username":"用户名","head_img":"http=>//example.com/a2.png","detail":"内容","create_time":"2017-11-13 23=>33=>00"},{"id":1,"username":"用户名","head_img":"http=>//example.com/a2.png","detail":"内容","create_time":"2017-11-13 23=>33=>00"}]
     * current_page : 1
     * has_next_page : true
     */

    public int current_page;
    public boolean has_next_page;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 1
         * username : 用户名
         * head_img : http=>//example.com/a2.png
         * detail : 内容
         * create_time : 2017-11-13 23=>33=>00
         */

        public int id;
        public String username;
        public String head_img;
        public String detail;
        public String create_time;
    }
}
