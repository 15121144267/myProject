package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.view.PresenterControl.OrderDetailControl;
import com.dispatching.feima.view.model.OrderDetailModel;
import com.dispatching.feima.view.model.ResponseData;

import javax.inject.Inject;

/**
 * Created by helei on 2017/4/28.
 * PresenterOrderDetailImpl
 */

public class PresenterOrderDetailImpl implements OrderDetailControl.PresenterOrderDetail {

    private OrderDetailControl.OrderDetailView mView;
    private final Context mContext;
    private final OrderDetailModel mModel;

    @Inject
    public PresenterOrderDetailImpl(Context context, OrderDetailModel model, OrderDetailControl.OrderDetailView view) {
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
