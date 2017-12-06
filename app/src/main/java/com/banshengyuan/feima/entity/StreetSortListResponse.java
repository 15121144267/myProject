package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/12/6.
 */

public class StreetSortListResponse implements Serializable {

    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 0
         * name : 全部店铺
         */

        public int id;
        public String name;
        public boolean select_position;
    }
}
