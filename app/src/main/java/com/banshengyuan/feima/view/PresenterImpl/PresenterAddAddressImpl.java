package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.AddAddressRequest;
import com.banshengyuan.feima.view.PresenterControl.AddAddressControl;
import com.banshengyuan.feima.view.model.AddAddressModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddAddressImpl
 */

public class PresenterAddAddressImpl implements AddAddressControl.PresenterAddAddress {
    private AddAddressControl.AddAddressView mView;
    private Context mContext;
    private AddAddressModel mModel;

    @Inject
    public PresenterAddAddressImpl(Context context, AddAddressControl.AddAddressView view, AddAddressModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void onCreate() {
    }


    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void requestAddressAdd(AddAddressRequest request, String token) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.addAddressRequest(request, token).compose(mView.applySchedulers())
                .subscribe(this::addAddressSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    @Override
    public void requestAddressUpdate(int addressId, AddAddressRequest request, String token) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.updateAddressRequest(addressId, request, token).compose(mView.applySchedulers())
                .subscribe(this::updateAddressSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }


    private void addAddressSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            mView.addAddressSuccess();
        } else if (responseData.resultCode == 100401 || responseData.resultCode == 100107) {
            mView.showToast("登入过期,请重新登入");
            mView.clearSwitchToLogin();
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    private void updateAddressSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            mView.updateAddressSuccess();
        } else if (responseData.resultCode == 100401 || responseData.resultCode == 100107) {
            mView.showToast("登入过期,请重新登入");
            mView.clearSwitchToLogin();
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }


}
