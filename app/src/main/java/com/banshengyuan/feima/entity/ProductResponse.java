package com.banshengyuan.feima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lei.he on 2017/11/1.
 */

public class ProductResponse implements Serializable {
    public String name;
    public List<ProductItemBean> mList;


    public static class ProductItemBean implements Serializable{
        public String content;
        public String tip;
    }
}
