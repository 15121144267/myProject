package com.banshengyuan.feima.entity;

import java.io.Serializable;

/**
 * Created by li.liu on 2017/12/21.
 */

public class GoodsCommentContentRequest implements Serializable{
    /**
     * goods_id : 88
     * content : 衣服很好
     */

    private int goods_id;
    private String content;

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
