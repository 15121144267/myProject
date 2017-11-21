package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/11/21.
 */

public class BlockDetailResponse implements Serializable {

    /**
     * info : {"id":1,"name":"街区1","summary":"这是街区的简介","cover_img":"http://example.com/1.png","address":"街区地址","top_img":["http://example.com/1.png","http://example.com/2.png","http://example.com/3.png"],"is_collected":false}
     */

    public InfoBean info;

    public static class InfoBean {
        /**
         * id : 1
         * name : 街区1
         * summary : 这是街区的简介
         * cover_img : http://example.com/1.png
         * address : 街区地址
         * top_img : ["http://example.com/1.png","http://example.com/2.png","http://example.com/3.png"]
         * is_collected : false
         */

        public int id;
        public String name;
        public String summary;
        public String cover_img;
        public String address;
        public boolean is_collected;
        public List<String> top_img;
    }
}
