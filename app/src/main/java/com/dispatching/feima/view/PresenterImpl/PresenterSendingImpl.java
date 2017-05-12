package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.DeliveryStatusResponse;
import com.dispatching.feima.entity.OrderDeliveryResponse;
import com.dispatching.feima.view.PresenterControl.SendingOrderControl;
import com.dispatching.feima.view.model.MainModel;
import com.dispatching.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/5/3.
 * PresenterSendingImpl
 */

public class PresenterSendingImpl implements SendingOrderControl.PresenterSendingOrder {
    private MainModel mMainModel;
    private SendingOrderControl.SendingOrderView mView;
    private Context mContext;

    @Inject
    public PresenterSendingImpl(Context context, MainModel model) {
        mContext = context;
        mMainModel = model;
    }

    @Override
    public void requestSendingOrder(Integer position, String token, String version, String uId) {
        mView.showLoading(mContext.getString(R.string.loading));
        mMainModel.SendingOrderInfoRequest(token, version, uId).compose(mView.applySchedulers())
                .subscribe(responseData -> getSendingOrderSuccess(responseData)
                        , throwable -> mView.getOrderError(throwable), () -> mView.getOrderComplete());
    }

    @Override
    public void requestCompleteOrder(String token, String version, String uId, String deliveryId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mMainModel.TakeOrderRequest(1,token, version, uId,deliveryId).compose(mView.applySchedulers())
                .subscribe(responseData -> getCompleteOrderSuccess(responseData)
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getCompleteOrderSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            responseData.parseData(DeliveryStatusResponse.class);
            DeliveryStatusResponse response = (DeliveryStatusResponse) responseData.parsedData;
            mView.updateOrderStatusSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
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
    public void setView(SendingOrderControl.SendingOrderView sendingOrderView) {
        mView = sendingOrderView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
