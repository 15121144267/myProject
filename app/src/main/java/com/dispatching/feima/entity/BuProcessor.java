package com.dispatching.feima.entity;

import javax.inject.Inject;

/**
 * Created by helei on 2017/5/3.
 * BuProcessor
 */

public class BuProcessor {

    private String userToken;
    private String userId;

    private ShopDetailResponse.ProductsBean mGoodsInfo;
    private String userPhone;
    private ShopListResponse.ListBean shopInfo;
    private PersonInfoResponse personInfo;
    private String mPartnerId;
    @Inject
    public BuProcessor() {
    }

    public String getPartnerId() {
        return mPartnerId;
    }

    public void setPartnerId(String partnerId) {
        mPartnerId = partnerId;
    }

    public PersonInfoResponse getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfoResponse personInfo) {
        this.personInfo = personInfo;
    }

    public ShopDetailResponse.ProductsBean getGoodsInfo() {
        return mGoodsInfo;
    }

    public void setGoodsInfo(ShopDetailResponse.ProductsBean goodsInfo) {
        mGoodsInfo = goodsInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public ShopListResponse.ListBean getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(ShopListResponse.ListBean shopInfo) {
        this.shopInfo = shopInfo;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
