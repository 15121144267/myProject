package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.OrderDeliveryResponse;
import com.dispatching.feima.view.PresenterControl.PendingOrderControl;
import com.dispatching.feima.view.model.MainModel;
import com.dispatching.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/5/3.
 * PresenterPendingImpl
 */

public class PresenterPendingImpl implements PendingOrderControl.PresenterPendingOrder {
    private final MainModel mMainModel;
    private PendingOrderControl.PendingOrderView mView;
    private final Context mContext;

    @Inject
    public PresenterPendingImpl(Context context, MainModel model) {
        mContext = context;
        mMainModel = model;
    }

    @Override
    public void requestPendingOrder(String token, String uId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mMainModel.PendingOrderInfoRequest(token, uId).compose(mView.applySchedulers())
                .subscribe(this::getPendingOrderSuccess
                        , throwable -> mView.getOrderError(throwable), () -> mView.getPendingOrderComplete());
        mView.addSubscription(disposable);
    }

    @Override
    public void requestTakeOrder(String token, String uId, String deliveryId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mMainModel.TakeOrderRequest(0,token, uId,deliveryId).compose(mView.applySchedulers())
                .subscribe(this::getTakeOrderSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    @Override
    public void requestUpOrder(String businessId) {
        mMainModel.updateDb(businessId).compose(mView.applySchedulers()).subscribe(this::updateSuccess,
                throwable -> mView.showErrMessage(throwable));
    }

    private void updateSuccess(Integer count) {

    }

    private void getTakeOrderSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            /*responseData.parseData(DeliveryStatusResponse.class);
            DeliveryStatusResponse response = (DeliveryStatusResponse) responseData.parsedData;*/
            mView.updateOrderStatusSuccess();
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    private void getPendingOrderSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            responseData.parseData(OrderDeliveryResponse.class);
            OrderDeliveryResponse response = (OrderDeliveryResponse) responseData.parsedData;
            mView.getPendingOrderSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void setView(PendingOrderControl.PendingOrderView pendingOrderView) {
        mView = pendingOrderView;
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
