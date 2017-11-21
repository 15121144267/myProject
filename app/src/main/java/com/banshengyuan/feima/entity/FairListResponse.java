package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/11/21.
 */

public class FairListResponse implements Serializable {

    public List<CategoryBean> category;

    public static class CategoryBean {
        /**
         * id : 1
         * name : 市集分类名称
         * summary : 市集分类的简介
         * cover_img : http://example.com/1.png
         * fair : [{"id":11,"name":"市集名称","summary":"市集的简介","cover_img":"http://example.com/1.png"}]
         */

        public int id;
        public String name;
        public String summary;
        public String cover_img;
        public List<FairBean> fair;

        public static class FairBean {
            /**
             * id : 11
             * name : 市集名称
             * summary : 市集的简介
             * cover_img : http://example.com/1.png
             */

            public int id;
            public String name;
            public String summary;
            public String cover_img;
        }
    }
}
