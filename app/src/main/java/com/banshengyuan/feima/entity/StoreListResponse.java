package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/11/21.
 */

public class StoreListResponse implements Serializable {

    public List<CategoryBean> category;

    public static class CategoryBean {
        /**
         * id : 1
         * name : 店铺分类名字
         * cover_img : http://example.com/a2.png
         * goods : [{"id":1,"name":"店铺名字","cover_img":"http://example.com/a2.png"}]
         */

        public int id;
        public String name;
        public String cover_img;
        public List<GoodsBean> goods;

        public static class GoodsBean {
            /**
             * id : 1
             * name : 店铺名字
             * cover_img : http://example.com/a2.png
             */

            public int id;
            public String shop_name;
            public String shop_logo;
        }
    }
}
