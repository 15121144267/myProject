package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/11/22.
 */

public class AllFairListResponse implements Serializable {

    /**
     * list : [{"name":"品牌市集","list1":[{"id":1,"name":"市集1名字","flag":"品牌市集"},{"id":1,"name":"市集1名字","flag":"品牌市集"},{"id":1,"name":"市集1名字","flag":"品牌市集"}]},{"name":"线下市集","list1":[{"id":1,"name":"市集1名字","flag":"线下市集"},{"id":1,"name":"市集1名字","flag":"线下市集"},{"id":1,"name":"市集1名字","flag":"线下市集"}]},{"name":"其他市集","list1":[{"id":1,"name":"市集1名字","flag":"其他市集"},{"id":1,"name":"市集1名字","flag":"其他市集"},{"id":1,"name":"市集1名字","flag":"其他市集"}]}]
     * current_page : 1
     * has_next_page : true
     */

    public int current_page;
    public boolean has_next_page;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * name : 品牌市集
         * list1 : [{"id":1,"name":"市集1名字","flag":"品牌市集"},{"id":1,"name":"市集1名字","flag":"品牌市集"},{"id":1,"name":"市集1名字","flag":"品牌市集"}]
         */

        public String name;
        public List<List1Bean> list1;

        public static class List1Bean {
            /**
             * id : 1
             * name : 市集1名字
             * flag : 品牌市集
             */

            public int id;
            public String name;
            public String flag;
        }
    }
}
