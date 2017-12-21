package com.banshengyuan.feima.entity;

/**
 * Created by lei.he on 2017/7/5.
 * AddAddressRequest
 */

public class HotFariJoinActionResponse {


    /**
     * order_sn : H2017122101164710906
     * total_fee : 11100
     */

    private String order_sn;
    private int total_fee;

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }
}
