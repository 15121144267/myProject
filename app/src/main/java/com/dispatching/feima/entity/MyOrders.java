package com.dispatching.feima.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by helei on 2017/5/3.
 */

public class MyOrders implements Serializable {
    @SerializedName("order_id")
    public String orderId;

    @SerializedName("shop_id")
    public String shopId;

    @SerializedName("delivery_id")
    public String deliveryId;

    @SerializedName("business_id")
    public String businessId;

    @SerializedName("delivery_status")
    public Integer deliveryStatus;

    @SerializedName("postman_id")
    public String postmanId;

    @SerializedName("create_time")
    public String createTime;

    @SerializedName("customer_address")
    public String customerAddress;

    @SerializedName("customer_longitude")
    public double customerLongitude;

    @SerializedName("customer_latitude")
    public double customerLatitude;

    @SerializedName("shop_longitude")
    public double shopLongitude;

    @SerializedName("shop_latitude")
    public double shopLatitude;

    @SerializedName("shop_address")
    public String ShopAddress;

    @SerializedName("take_time")
    public String takeTime;

    @SerializedName("end_time")
    public String endTime;

    @SerializedName("total_fee")
    public double totalFee;

    public Integer status;
    public String remark;
    public String customer;
    public String phone;
    public String channel;
}
