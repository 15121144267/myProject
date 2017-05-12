package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.DeliveryStatusResponse;
import com.dispatching.feima.view.PresenterControl.OrderDetailControl;
import com.dispatching.feima.view.model.MainModel;
import com.dispatching.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/4/28.
 * PresenterOrderDetailImpl
 */

public class PresenterOrderDetailImpl implements OrderDetailControl.PresenterOrderDetail {
    private MainModel mMainModel;
    private OrderDetailControl.OrderDetailView mView;
    private Context mContext;
    @Inject
    public PresenterOrderDetailImpl(Context context,MainModel model) {
        mContext =context;
        mMainModel = model;
    }

    @Override
    public void requestUpdateOrder(Integer position, String token, String version, String uId, String delivery) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mMainModel.TakeOrderRequest(position, token, version, uId, delivery).compose(mView.applySchedulers())
                .subscribe(responseData -> updateSuccess(responseData)
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void updateSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            responseData.parseData(DeliveryStatusResponse.class);
            DeliveryStatusResponse response = (DeliveryStatusResponse) responseData.parsedData;
            mView.updateOrderStatusSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void setView(OrderDetailControl.OrderDetailView orderDetailView) {
        mView = orderDetailView;
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
