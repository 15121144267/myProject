package com.dispatching.feima.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by helei on 2017/5/3.
 */

public class MyOrders implements Serializable {
        @SerializedName("order_id")
        public String orderId;
        @SerializedName("fm_id")
        public String fmId;
        @SerializedName("posorder_id")
        public String posorderId;
        @SerializedName("delivery_status")
        public Integer deliveryStatus;
        @SerializedName("create_time")
        public long createTime;
        @SerializedName("customer_address")
        public String customerAddress;
        @SerializedName("Shop_address")
        public String ShopAddress;
        @SerializedName("total_fee")
        public float totalFee;

        public Integer status;
        public String remark;
        public String customer;
        public String phone;
        public String channel;
}
