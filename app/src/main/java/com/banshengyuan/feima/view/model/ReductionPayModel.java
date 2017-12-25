package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.entity.UnderPayRequest;
import com.banshengyuan.feima.network.networkapi.ReductionPayApi;
import com.banshengyuan.feima.utils.ValueUtil;
import com.google.gson.Gson;

import java.util.TreeMap;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class ReductionPayModel {
    private final ReductionPayApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;
    private final BuProcessor mBuProcessor;

    @Inject
    public ReductionPayModel(ReductionPayApi api, Gson gson, ModelTransform transform, BuProcessor buProcessor) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
        mBuProcessor = buProcessor;
    }

    public Observable<ResponseData> couponListRequest(String storeId, String status) {
        TreeMap<String, String> treeMap = new TreeMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        treeMap.put("timestamp", timestamp);
        treeMap.put("token", mBuProcessor.getUserToken());
        treeMap.put("status", status);
        String head = ValueUtil.getSign(treeMap,timestamp);
        return mApi.couponListRequest(mBuProcessor.getUserToken(), storeId, status,head).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> payConfirmRequest(String storeId, String amount, String discount, String payed) {
        UnderPayRequest request = new UnderPayRequest();
        request.store_id = storeId;
        request.amount = amount;
        request.discount = discount;
        request.payed = payed;
        request.token = mBuProcessor.getUserToken();
        return mApi.payConfirmRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }


}
