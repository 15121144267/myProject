package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.AddAddressRequest;
import com.dispatching.feima.entity.AddressResponse;
import com.dispatching.feima.view.PresenterControl.AddressControl;
import com.dispatching.feima.view.model.AddressModel;
import com.dispatching.feima.view.model.ResponseData;

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
    public void requestAddressDefault(AddressResponse.DataBean request) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.addressDefaultRequest(request).compose(mView.applySchedulers())
                .subscribe(this::addressDefaultSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    @Override
    public void requestAddressList(String phone) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.addressListRequest(phone).compose(mView.applySchedulers())
                .subscribe(this::addressListRequestSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void addressListRequestSuccess(ResponseData responseData) {
        if(responseData.resultCode == 100){
            responseData.parseData(AddressResponse.class);
            AddressResponse response  = (AddressResponse) responseData.parsedData;
            mView.addressListSuccess(response.data);
        }else {
            mView.showToast(responseData.errorDesc);
        }
    }
    private void addressDefaultSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            mView.addressDefaultSuccess();
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void requestDeleteAddress(AddAddressRequest request) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.deleteAddressRequest(request).compose(mView.applySchedulers())
                .subscribe(this::deleteAddressSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void deleteAddressSuccess(ResponseData responseData) {
        if(responseData.resultCode == 100){
            mView.deleteAddressSuccess();
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
