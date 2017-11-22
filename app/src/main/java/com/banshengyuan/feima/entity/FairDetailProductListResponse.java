package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/11/22.
 */

public class FairDetailProductListResponse implements Serializable {

    /**
     * list : [{"id":1,"name":"名字","cover_img":"http=>//example.com/a2.png","price":110},{"id":1,"name":"名字","cover_img":"http=>//example.com/a2.png","price":110},{"id":1,"name":"名字","cover_img":"http=>//example.com/a2.png","price":110}]
     * current_page : 1
     * has_next_page : true
     */

    public int current_page;
    public boolean has_next_page;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 1
         * name : 名字
         * cover_img : http=>//example.com/a2.png
         * price : 110
         */

        public int id;
        public String name;
        public String cover_img;
        public int price;
    }
}
