package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/6/30.
 * ShopDetailResponse
 */

public class StoreDetailResponse implements Serializable {

    /**
     * info : {"id":1,"name":"大创生活馆","address":"安徽省xxxxxx","mobile":"13812341234","top_img":["http=>//example.com/a1.png","http=>//example.com/a2.png","http=>//example.com/a3.png"],"summary":"店铺简介","is_collected":false}
     */

    public InfoBean info;

    public static class InfoBean {
        /**
         * id : 1
         * name : 大创生活馆
         * address : 安徽省xxxxxx
         * mobile : 13812341234
         * top_img : ["http=>//example.com/a1.png","http=>//example.com/a2.png","http=>//example.com/a3.png"]
         * summary : 店铺简介
         * is_collected : false
         */

        public int id;
        public String name;
        public String address;
        public String mobile;
        public String summary;
        public boolean is_collected;
        public boolean is_catering;
        public List<String> top_img;
    }
}
