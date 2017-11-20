package com.banshengyuan.feima.entity;

import java.io.Serializable;

/**
 * Created by lei.he on 2017/11/20.
 */

public class RecommendTopResponse implements Serializable {

    /**
     * info : {"id":1,"name":"街区1","distance":"1400","cover_img":"http=>//example.com/1.png"}
     */

    public InfoBean info;

    public static class InfoBean {
        /**
         * id : 1
         * name : 街区1
         * distance : 1400
         * cover_img : http=>//example.com/1.png
         */

        public int id;
        public String name;
        public String distance;
        public String cover_img;
    }
}
