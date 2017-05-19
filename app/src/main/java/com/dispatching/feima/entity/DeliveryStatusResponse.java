package com.dispatching.feima.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by helei on 2017/5/3.
 * DeliveryStatusResponse
 */

public class DeliveryStatusResponse implements Serializable{
    @SerializedName("delivery_id")
    public String deliveryId;

    @SerializedName("delivery_status")
    public String deliveryStatus;

}
