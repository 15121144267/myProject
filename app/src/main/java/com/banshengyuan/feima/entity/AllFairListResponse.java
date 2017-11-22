package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/11/22.
 */

public class AllFairListResponse implements Serializable {

    /**
     * list : {"brand":[{"id":1,"name":"市集1名字"},{"id":1,"name":"市集1名字"},{"id":1,"name":"市集1名字"}],"other":[{"id":1,"name":"市集1名字"},{"id":1,"name":"市集1名字"},{"id":1,"name":"市集1名字"}],"street":[{"id":1,"name":"市集1名字"},{"id":1,"name":"市集1名字"},{"id":1,"name":"市集1名字"}]}
     * current_page : 1
     * has_next_page : true
     */

    public ListBean list;
    public int current_page;
    public boolean has_next_page;

    public static class ListBean {
        public List<BrandBean> brand;
        public List<OtherBean> other;
        public List<StreetBean> street;

        public static class BrandBean {
            /**
             * id : 1
             * name : 市集1名字
             */

            public int id;
            public String name;
        }

        public static class OtherBean {
            /**
             * id : 1
             * name : 市集1名字
             */

            public int id;
            public String name;
        }

        public static class StreetBean {
            /**
             * id : 1
             * name : 市集1名字
             */

            public int id;
            public String name;
        }
    }
}
