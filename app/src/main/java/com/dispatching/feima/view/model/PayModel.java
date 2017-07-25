package com.dispatching.feima.view.model;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.entity.BuProcessor;
import com.dispatching.feima.entity.OrderConfirmedRequest;
import com.dispatching.feima.entity.PayRequest;
import com.dispatching.feima.entity.ShopDetailResponse;
import com.dispatching.feima.entity.ShopListResponse;
import com.dispatching.feima.entity.SpecificationResponse;
import com.dispatching.feima.network.networkapi.PayApi;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class PayModel {
    private final PayApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;
    private final BuProcessor mBuProcessor;
    private final Context mContext;
    private AMapLocation mLocationInfo;
    private final String partnerId = "53c69e54-c788-495c-bed3-2dbfc6fd5c61_";
    private ShopListResponse.ListBean mBean;
    private ShopDetailResponse.ProductsBean mGoodsInfo;

    @Inject
    public PayModel(PayApi api, Gson gson, ModelTransform transform, BuProcessor buProcessor, Context context) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
        mBuProcessor = buProcessor;
        mContext = context;
        mLocationInfo = ((DaggerApplication) mContext.getApplicationContext()).getaMapLocation();
    }


    public Observable<ResponseData> orderConfirmedRequest(OrderConfirmedRequest request, SpecificationResponse.ProductsBean.ProductSpecificationBean productSpecification) {
        mBean = mBuProcessor.getShopInfo();
        mGoodsInfo = mBuProcessor.getGoodsInfo();

        List<OrderConfirmedRequest.ProductsBean> list = new ArrayList<>();
        OrderConfirmedRequest.ProductsBean productsBean = new OrderConfirmedRequest.ProductsBean();
        productsBean.productName = mGoodsInfo.name;
        productsBean.number = String.valueOf(productSpecification.count);
        productsBean.sequence = "0";

        productsBean.specification = productSpecification.specification;
        productsBean.productId = productSpecification.productId + "";
        productsBean.price = mGoodsInfo.finalPrice * productSpecification.count;
        list.add(productsBean);

        List<OrderConfirmedRequest.AccountsBean> accountList = new ArrayList<>();
        OrderConfirmedRequest.AccountsBean accountsBean = new OrderConfirmedRequest.AccountsBean();
        accountsBean.sequence = 0;
        accountsBean.accountId = "123456";
        accountsBean.number = "1";
        accountsBean.name = "运费";
        accountsBean.type = "1";
        accountsBean.price = "500";
        accountList.add(accountsBean);

        request.shopName = mBean.storeName;
        request.source = "android";
        request.address = mBean.address;
        request.customerOrder = "BSY_" + System.currentTimeMillis();
        request.userName = mBuProcessor.getUserPhone();
        request.products = list;
        request.amount = 1000;
        request.type = 1;
        request.payType = 1;
        request.userId = mBuProcessor.getUserId();
        request.payChannel = "";
        if(mLocationInfo!=null){
            request.longitude = String.valueOf(mLocationInfo.getLongitude());
            request.latitude = String.valueOf(mLocationInfo.getLatitude());
        }
        request.status = 1;
        request.shopId = partnerId + mBean.storeCode;
        request.accounts = accountList;
        request.partition = "";
        request.remark = "";
        request.payChannelName = "";
        request.companyId = mGoodsInfo.companyId;

        return mApi.orderConfirmedRequest(mGson.toJson(request)).map(mTransform::transformTypeTwo);
    }

    public Observable<ResponseData> payRequest(long oid, String payCode) {
        PayRequest request = new PayRequest();
        request.orderId = oid;
        request.pay_ebcode = payCode;
        return mApi.payRequest(mGson.toJson(request)).map(mTransform::transformTypeTwo);
    }

}
