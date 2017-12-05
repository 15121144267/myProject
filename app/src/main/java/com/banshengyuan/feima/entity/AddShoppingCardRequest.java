package com.banshengyuan.feima.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lei.he on 2017/8/9.
 * AddShoppingCardRequest
 */

public class AddShoppingCardRequest {
    @SerializedName("goods_id")
    public String goodsId;
    @SerializedName("goods_sku")
    public String goodsSku;
    @SerializedName("goods_number")
    public String goodsNumber;
    public String token;

}
