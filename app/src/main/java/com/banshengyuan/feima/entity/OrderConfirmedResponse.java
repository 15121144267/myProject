package com.banshengyuan.feima.entity;

import java.io.Serializable;

/**
 * Created by lei.he on 2017/7/13.
 * OrderConfirmedResponse
 */

public class OrderConfirmedResponse implements Serializable {

    /**
     * order_sn : 2017110421470012
     * total_fee : 10021
     */

    public String order_sn;
    public int total_fee;
    public int channel;
}
