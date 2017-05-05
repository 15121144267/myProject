package com.dispatching.feima.view.PresenterImpl;

import com.dispatching.feima.entity.OrderDeliveryResponse;
import com.dispatching.feima.view.PresenterControl.CompletedOrderControl;
import com.dispatching.feima.view.model.MainModel;
import com.dispatching.feima.view.model.ResponseData;

import java.net.ConnectException;

import javax.inject.Inject;

import retrofit2.HttpException;

/**
 * Created by helei on 2017/5/3.
 */

public class PresenterCompletedImpl implements CompletedOrderControl.PresenterCompletedOrder {
    private MainModel mMainModel;
    private CompletedOrderControl.CompletedOrderView mView;

    @Inject
    public PresenterCompletedImpl(MainModel model) {
        mMainModel = model;
    }

    @Override
    public void requestCompletedOrder(Integer position, String token, String version, String uId) {
        mView.showLoading("加载中...");
        mMainModel.OrderInfoRequest(position, token, version, uId).compose(mView.applySchedulers())
                .subscribe(responseData -> getPendingOrderSuccess(responseData)
                        , throwable -> showErrMessage(throwable), () -> mView.dismissLoading());
    }

    private void getPendingOrderSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            responseData.parseData(OrderDeliveryResponse.class);
            OrderDeliveryResponse response = (OrderDeliveryResponse) responseData.parsedData;
            mView.getCompletedOrderSuccess(response);
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
    public void setView(CompletedOrderControl.CompletedOrderView completedOrderView) {
        mView = completedOrderView;
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
