package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/12/4.
 */

public class ShopSortListResponse implements Serializable {

    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 1
         * name : 餐饮美食
         * is_recommend : 2
         */

        public int id;
        public String name;
        public int is_recommend;
        public boolean select_position;
    }
}
