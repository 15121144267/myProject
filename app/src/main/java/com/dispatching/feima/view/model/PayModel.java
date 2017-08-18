package com.dispatching.feima.view.model;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.entity.BuProcessor;
import com.dispatching.feima.entity.OrderConfirmedRequest;
import com.dispatching.feima.entity.PayAccessRequest;
import com.dispatching.feima.entity.PayRequest;
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
    private final String partnerId = BuildConfig.PARTNER_ID+"_";
    private ShopListResponse.ListBean mBean;

    @Inject
    public PayModel(PayApi api, Gson gson, ModelTransform transform, BuProcessor buProcessor, Context context) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
        mBuProcessor = buProcessor;
        mContext = context;
        mLocationInfo = ((DaggerApplication) mContext.getApplicationContext()).getaMapLocation();
    }


    public Observable<ResponseData> orderConfirmedRequest(OrderConfirmedRequest request, SpecificationResponse productSpecification) {
        mBean = mBuProcessor.getShopInfo();

        List<OrderConfirmedRequest.ProductsBean> list = new ArrayList<>();
        for (SpecificationResponse.ProductsBean product : productSpecification.products) {
            OrderConfirmedRequest.ProductsBean productsBean = new OrderConfirmedRequest.ProductsBean();
            productsBean.productName = product.name;
            productsBean.sequence = product.sequence + "";
            productsBean.number = String.valueOf(product.saleCount);
            productsBean.specification = product.specification;
            productsBean.productId = product.pid;
            productsBean.price = product.finalPrice;
            list.add(productsBean);
        }


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
        request.customerOrder = "BSY_" + System.currentTimeMillis();
        request.products = list;
        request.amount = 1000;
        request.type = 1;
        request.payType = 1;
        request.userId = mBuProcessor.getUserId();
        request.payChannel = "";
        if (mLocationInfo != null) {
            request.longitude = String.valueOf(mLocationInfo.getLongitude());
            request.latitude = String.valueOf(mLocationInfo.getLatitude());
        }
        request.status = 1;
        request.shopId = partnerId + mBean.storeCode;
        request.accounts = accountList;
        request.partition = "";
        request.remark = "";
        request.payChannelName = "";
        request.companyId = productSpecification.products.get(0).companyId;

        return mApi.orderConfirmedRequest(mGson.toJson(request)).map(mTransform::transformTypeTwo);
    }

    public Observable<ResponseData> payRequest(long oid, String payCode) {
        PayRequest request = new PayRequest();
        request.orderId = oid;
        request.pay_ebcode = payCode;
        return mApi.payRequest(mGson.toJson(request)).map(mTransform::transformTypeTwo);
    }

    public Observable<ResponseData> updateOrderStatusRequest(String oid) {
        PayAccessRequest request = new PayAccessRequest();
        request.orderId = oid;
        return mApi.updateOrderStatusRequest(mGson.toJson(request)).map(mTransform::transformTypeTwo);
    }

}
