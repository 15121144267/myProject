package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.view.PresenterControl.FairDetailControl;
import com.banshengyuan.feima.view.model.FairDetailModel;

import javax.inject.Inject;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddAddressImpl
 */

public class PresenterFairDetailImpl implements FairDetailControl.PresenterFairDetail {
    private FairDetailControl.FairDetailView mView;
    private Context mContext;
    private FairDetailModel mModel;

    @Inject
    public PresenterFairDetailImpl(Context context, FairDetailControl.FairDetailView view, FairDetailModel model) {
        mContext = context;
        mView = view;
        mModel = model;
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
