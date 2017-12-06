package com.banshengyuan.feima.entity;

import java.io.Serializable;

/**
 * Created by lei.he on 2017/12/6.
 */

public class FairCategoryResponse implements Serializable {

    /**
     * info : {"id":1,"name":"品牌市集","summary":"","cover_img":"http://ssapp.jixuanjk.com/"}
     */

    public InfoBean info;

    public static class InfoBean {
        /**
         * id : 1
         * name : 品牌市集
         * summary :
         * cover_img : http://ssapp.jixuanjk.com/
         */

        public int id;
        public String name;
        public String summary;
        public String cover_img;
    }
}
