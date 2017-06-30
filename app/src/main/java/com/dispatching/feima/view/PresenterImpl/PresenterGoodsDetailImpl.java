package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.view.PresenterControl.GoodsDetailControl;
import com.dispatching.feima.view.model.GoodsDetailModel;
import com.dispatching.feima.view.model.ResponseData;

import javax.inject.Inject;

/**
 * Created by helei on 2017/4/28.
 * PresenterOrderDetailImpl
 */

public class PresenterGoodsDetailImpl implements GoodsDetailControl.PresenterGoodsDetail {

    private GoodsDetailControl.GoodsDetailView mView;
    private final Context mContext;
    private final GoodsDetailModel mModel;

    @Inject
    public PresenterGoodsDetailImpl(Context context, GoodsDetailModel model, GoodsDetailControl.GoodsDetailView view) {
        mContext =context;
        mModel = model;
        mView = view;
    }


    private void updateSuccess(ResponseData responseData) {
       /* if (responseData.resultCode == 100) {
          *//*  responseData.parseData(DeliveryStatusResponse.class);
            DeliveryStatusResponse response = (DeliveryStatusResponse) responseData.parsedData;*//*
            mView.updateOrderStatusSuccess();
        } else {
            mView.showToast(responseData.errorDesc);
        }*/
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
