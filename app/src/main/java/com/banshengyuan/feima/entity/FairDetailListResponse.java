package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/11/22.
 */

public class FairDetailListResponse implements Serializable {

    /**
     * list : [{"id":1,"name":"市集1名字","cover_img":"http=>//example.com/a2.png","create_time":"1510036420"},{"id":1,"name":"市集1名字","cover_img":"http=>//example.com/a2.png","create_time":"1510036420"},{"id":1,"name":"市集1名字","cover_img":"http=>//example.com/a2.png","create_time":"1510036420"}]
     * current_page : 1
     * has_next_page : true
     */

    public int current_page;
    public boolean has_next_page;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 1
         * name : 市集1名字
         * cover_img : http=>//example.com/a2.png
         * create_time : 1510036420
         */

        public int id;
        public String name;
        public String cover_img;
        public String create_time;
    }
}
