package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MyCoupleResponse;
import com.banshengyuan.feima.entity.OrderConfirmedResponse;
import com.banshengyuan.feima.view.PresenterControl.ReductionPayControl;
import com.banshengyuan.feima.view.model.ReductionPayModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddAddressImpl
 */

public class PresenterReductionPayImpl implements ReductionPayControl.PresenterReductionPay {
    private ReductionPayControl.ReductionPayView mView;
    private Context mContext;
    private ReductionPayModel mModel;

    @Inject
    public PresenterReductionPayImpl(Context context, ReductionPayControl.ReductionPayView view, ReductionPayModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void requestPay(String storeId, String amount, String discount, String payed) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.payConfirmRequest(storeId, amount, discount, payed).compose(mView.applySchedulers())
                .subscribe(this::getPayRequestSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getPayRequestSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(OrderConfirmedResponse.class);
            OrderConfirmedResponse response = (OrderConfirmedResponse) responseData.parsedData;
            mView.getPayRequestSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
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
        if (responseData.resultCode == 200) {
            responseData.parseData(MyCoupleResponse.class);
            MyCoupleResponse response = (MyCoupleResponse) responseData.parsedData;
            mView.getCouponListRequestSuccess(response);
        } else if (responseData.resultCode == 100401 || responseData.resultCode == 100107) {
            mView.clearSwitchToLogin();
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
