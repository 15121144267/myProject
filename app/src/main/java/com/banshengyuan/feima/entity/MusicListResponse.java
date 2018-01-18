package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/11/20.
 */

public class MusicListResponse implements Serializable {

    public List<FairBean> fair;
    public List<HotBean> hot;

    public static class FairBean {
        /**
         * id : 1
         * name : 市集名字1
         * cover_img : http=>//example.com/a2.png
         */

        public int id;
        public String name;
        public String cover_img;
    }

    public static class HotBean {
        /**
         * id : 1
         * name : 热闹名字1
         * cover_img : http=>//example.com/a2.png
         */

        public int id;
        public String name;
        public String cover_img;
        public int status;
    }
}
