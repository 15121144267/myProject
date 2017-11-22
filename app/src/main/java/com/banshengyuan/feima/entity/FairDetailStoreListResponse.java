package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/11/22.
 */

public class FairDetailStoreListResponse implements Serializable {

    /**
     * list : [{"id":11,"name":"店铺名字","cover_img":"http://example.com/a2.png","category":"分类名字"},{"id":11,"name":"店铺名字","cover_img":"http://example.com/a2.png","category":"分类名字"},{"id":11,"name":"店铺名字","cover_img":"http://example.com/a2.png","category":"分类名字"}]
     * current_page : 1
     * has_next_page : true
     */

    public int current_page;
    public boolean has_next_page;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 11
         * name : 店铺名字
         * cover_img : http://example.com/a2.png
         * category : 分类名字
         */

        public int id;
        public String name;
        public String cover_img;
        public String category;
    }
}
