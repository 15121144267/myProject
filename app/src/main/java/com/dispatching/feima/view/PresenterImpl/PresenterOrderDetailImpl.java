package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.R;
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
    private final MainModel mMainModel;
    private OrderDetailControl.OrderDetailView mView;
    private final Context mContext;
    @Inject
    public PresenterOrderDetailImpl(Context context,MainModel model) {
        mContext =context;
        mMainModel = model;
    }

    @Override
    public void requestUpdateOrder(Integer position, String token, String uId, String delivery) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mMainModel.TakeOrderRequest(position, token, uId, delivery).compose(mView.applySchedulers())
                .subscribe(this::updateSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void updateSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
          /*  responseData.parseData(DeliveryStatusResponse.class);
            DeliveryStatusResponse response = (DeliveryStatusResponse) responseData.parsedData;*/
            mView.updateOrderStatusSuccess();
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
