package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.entity.OrderDeliveryResponse;
import com.banshengyuan.feima.view.PresenterControl.SendingOrderControl;
import com.banshengyuan.feima.view.model.MainModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

/**
 * Created by helei on 2017/5/3.
 * PresenterSendingImpl
 */

public class PresenterSendingImpl implements SendingOrderControl.PresenterSendingOrder {
    private final MainModel mMainModel;
    private SendingOrderControl.SendingOrderView mView;
    private final Context mContext;

    @Inject
    public PresenterSendingImpl(Context context, MainModel model,SendingOrderControl.SendingOrderView view) {
        mContext = context;
        mMainModel = model;
        mView = view;
    }

    @Override
    public void requestSendingOrder(String token, String uId) {
       /* mView.showLoading(mContext.getString(R.string.loading));
        mMainModel.SendingOrderInfoRequest(token, uId).compose(mView.applySchedulers())
                .subscribe(this::getSendingOrderSuccess
                        , throwable -> mView.getOrderError(throwable), () -> mView.getOrderComplete());*/
    }

    @Override
    public void requestCompleteOrder(String token, String uId, String deliveryId) {
       /* mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mMainModel.TakeOrderRequest(1,token, uId,deliveryId).compose(mView.applySchedulers())
                .subscribe(this::getCompleteOrderSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);*/
    }


    private void getSendingOrderSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            responseData.parseData(OrderDeliveryResponse.class);
            OrderDeliveryResponse response = (OrderDeliveryResponse) responseData.parsedData;
            mView.getSendingOrderSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
