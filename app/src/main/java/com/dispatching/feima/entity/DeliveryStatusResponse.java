package com.dispatching.feima.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by helei on 2017/5/3.
 */

public class DeliveryStatusResponse implements Serializable{
    @SerializedName("order_id")
    public String orderId;

    @SerializedName("delivery_status")
    public String deliveryStatus;

    public String status;

}
