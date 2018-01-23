package com.banshengyuan.feima.entity;

/**
 * Created by li.liu on 2018/1/23.
 */

public class AddressRequest {
    private String addressId;
    private String token;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
