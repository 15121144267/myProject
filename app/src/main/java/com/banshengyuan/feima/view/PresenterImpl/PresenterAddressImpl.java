package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.AddAddressRequest;
import com.banshengyuan.feima.entity.AddressResponse;
import com.banshengyuan.feima.view.PresenterControl.AddressControl;
import com.banshengyuan.feima.view.model.AddressModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddressImpl
 */

public class PresenterAddressImpl implements AddressControl.PresenterAddress {
    private AddressControl.AddressView mView;
    private Context mContext;
    private AddressModel mModel;

    @Inject
    public PresenterAddressImpl(Context context, AddressControl.AddressView view, AddressModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void requestAddressList(String token) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.listAddressRequest(token).compose(mView.applySchedulers())
                .subscribe(this::addressListRequestSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    @Override
    public void requestDeleteAddress(String addressId, String token) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.deleteAddressRequest(addressId,token).compose(mView.applySchedulers())
                .subscribe(this::deleteAddressSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    @Override
    public void requestUpdateAddress(String addressId, AddAddressRequest request , String token) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.updateAddressRequest(addressId,request,token).compose(mView.applySchedulers())
                .subscribe(this::updateAddressSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }


    private void addressListRequestSuccess(ResponseData responseData) {
        if(responseData.resultCode == 200){
            responseData.parseData(AddressResponse.class);
            AddressResponse response  = (AddressResponse) responseData.parsedData;
            mView.listAddressSuccess(response);
        }else {
            mView.showToast(responseData.errorDesc);
        }
    }

    private void deleteAddressSuccess(ResponseData responseData) {
        if(responseData.resultCode == 200){
            mView.deleteAddressSuccess();
        }else {
            mView.showToast(responseData.errorDesc);
        }
    }

    private void updateAddressSuccess(ResponseData responseData) {
        if(responseData.resultCode == 200){
            mView.updateAddressSuccess();
        }else {
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
