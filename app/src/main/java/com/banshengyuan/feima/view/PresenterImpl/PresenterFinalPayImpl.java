package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.PayResponse;
import com.banshengyuan.feima.view.PresenterControl.FinalPayControl;
import com.banshengyuan.feima.view.model.FinalPayModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddressImpl
 */

public class PresenterFinalPayImpl implements FinalPayControl.PresenterFinalPay {
    private FinalPayControl.FinalPayView mView;
    private Context mContext;
    private FinalPayModel mModel;

    @Inject
    public PresenterFinalPayImpl(Context context, FinalPayControl.FinalPayView view, FinalPayModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void requestPayInfo(String orderId, Integer payType,Integer orderType) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.payRequest(orderId, payType,orderType).compose(mView.applySchedulers())
                .subscribe(this::getPayInfoSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getPayInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            responseData.parseData(PayResponse.class);
            PayResponse response = (PayResponse) responseData.parsedData;
            mView.orderPayInfoSuccess(response);
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
