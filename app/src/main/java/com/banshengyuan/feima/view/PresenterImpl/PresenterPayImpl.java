package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.AddressResponse;
import com.banshengyuan.feima.entity.MyCoupleResponse;
import com.banshengyuan.feima.entity.OrderConfirmItem;
import com.banshengyuan.feima.entity.OrderConfirmedResponse;
import com.banshengyuan.feima.entity.PayAccessRequest;
import com.banshengyuan.feima.view.PresenterControl.PayControl;
import com.banshengyuan.feima.view.model.PayModel;
import com.banshengyuan.feima.view.model.ResponseData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterPayImpl
 */

public class PresenterPayImpl implements PayControl.PresenterPay {
    private PayControl.PayView mView;
    private Context mContext;
    private PayModel mModel;

    @Inject
    public PresenterPayImpl(Context context, PayControl.PayView view, PayModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void requestCouponList(String storeId, String status) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.couponListRequest(storeId, status).compose(mView.applySchedulers())
                .subscribe(this::getCouponListRequestSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getCouponListRequestSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            responseData.parseData(MyCoupleResponse.class);
            MyCoupleResponse response = (MyCoupleResponse) responseData.parsedData;
            mView.getCouponListRequestSuccess(response);
        } else {
            mView.getCouponListRequestFail(responseData.errorDesc);
        }
    }

    @Override
    public void requestAddressList(String token) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.listAddressRequest(token).compose(mView.applySchedulers())
                .subscribe(this::addressListRequestSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void addressListRequestSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            responseData.parseData(AddressResponse.class);
            AddressResponse response = (AddressResponse) responseData.parsedData;
            mView.listAddressSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void requestOrderConfirmed(String addressId, List<OrderConfirmItem> list, Integer self) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.orderConfirmedRequest(addressId, list, self).compose(mView.applySchedulers())
                .subscribe(this::orderConfirmedSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void orderConfirmedSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(OrderConfirmedResponse.class);
            OrderConfirmedResponse response = (OrderConfirmedResponse) responseData.parsedData;
            mView.orderConfirmedSuccess(response);
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
