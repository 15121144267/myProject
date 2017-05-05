package com.dispatching.feima.view.PresenterImpl;

import com.dispatching.feima.entity.OrderDeliveryResponse;
import com.dispatching.feima.view.PresenterControl.PendingOrderControl;
import com.dispatching.feima.view.model.MainModel;
import com.dispatching.feima.view.model.ResponseData;

import java.net.ConnectException;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by helei on 2017/5/3.
 */

public class PresenterPendingImpl implements PendingOrderControl.PresenterPendingOrder {
    private MainModel mMainModel;
    PendingOrderControl.PendingOrderView mView;

    @Inject
    public PresenterPendingImpl(MainModel model) {
        mMainModel = model;
    }

    @Override
    public void requestPendingOrder(Integer position, String token, String version, String uId) {
        mView.showLoading("加载中...");
       Disposable disposable = mMainModel.OrderInfoRequest(position, token, version, uId).compose(mView.applySchedulers())
                .subscribe(responseData -> getPendingOrderSuccess(responseData)
                        , throwable -> showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
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

    private void showErrMessage(Throwable e) {
        mView.dismissLoading();
        if (e instanceof HttpException || e instanceof ConnectException
                || e instanceof RuntimeException) {
            mView.showToast("请检查网络");
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
