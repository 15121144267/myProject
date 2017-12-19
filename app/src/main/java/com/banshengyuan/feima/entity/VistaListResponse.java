package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/11/20.
 */

public class VistaListResponse implements Serializable {


    /**
     * list : [{"id":19,"name":"音乐小镇","summary":"以音乐为主题，集多种艺术创意业态于一体的浪漫艺术风情园","cover_img":"http://ssapp.jixuanjk.com/uploads/5a2f3460ad51719437.png","distance":9956853.9506882},{"id":17,"name":"半生缘潮流街区","summary":"带你领略潮流艺术的新时尚","cover_img":"http://ssapp.jixuanjk.com/uploads/5a2f323083b9951784.png","distance":9968775.0024227},{"id":18,"name":"齐云山齐云小镇","summary":"在一片诗情画意的景色中感受徽州人文魅力","cover_img":"http://ssapp.jixuanjk.com/uploads/5a2f333e4bfd078833.png","distance":1.0187448286632E7}]
     * current_page : 1
     * has_next_page : false
     */

    public int current_page;
    public boolean has_next_page;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 19
         * name : 音乐小镇
         * summary : 以音乐为主题，集多种艺术创意业态于一体的浪漫艺术风情园
         * cover_img : http://ssapp.jixuanjk.com/uploads/5a2f3460ad51719437.png
         * distance : 9956853.9506882
         */

        public int id;
        public String name;
        public String summary;
        public String cover_img;
        public double distance;
    }
}
