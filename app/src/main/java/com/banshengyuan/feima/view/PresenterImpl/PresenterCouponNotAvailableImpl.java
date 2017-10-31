package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.view.PresenterControl.CouponNotAvailableControl;
import com.banshengyuan.feima.view.model.CoupleModel;

import javax.inject.Inject;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddAddressImpl
 */

public class PresenterCouponNotAvailableImpl implements CouponNotAvailableControl.PresenterCouponNotAvailable {
    private CouponNotAvailableControl.CouponNotAvailableView mView;
    private Context mContext;
    private CoupleModel mModel;

    @Inject
    public PresenterCouponNotAvailableImpl(Context context, CouponNotAvailableControl.CouponNotAvailableView view,CoupleModel coupleModel) {
        mContext = context;
        mView = view;
        mModel = coupleModel;
    }

    /*@Override
    public void requestAddAddress(AddAddressRequest request) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.addAddressRequest(request).compose(mView.applySchedulers())
                .subscribe(this::addAddressSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void addAddressSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            mView.addAddressSuccess();
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }*/

    @Override
    public void onCreate() {

    }


    @Override
    public void onDestroy() {
        mView = null;
    }
}
