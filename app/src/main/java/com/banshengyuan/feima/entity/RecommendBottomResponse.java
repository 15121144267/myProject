package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/11/20.
 */

public class RecommendBottomResponse implements Serializable {

    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 1
         * name : 市集1名字
         * cover_img : http://example.com/a2.png
         * create_time : 1510036420
         */

        public int id;
        public String name;
        public String cover_img;
        public String create_time;
    }
}
