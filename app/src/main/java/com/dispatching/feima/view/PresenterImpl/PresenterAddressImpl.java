package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.view.PresenterControl.AddressControl;
import com.dispatching.feima.view.model.AddressModel;
import com.dispatching.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
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
    public void requestAddressList(String phone) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.addressListRequest(phone).compose(mView.applySchedulers())
                .subscribe(this::addressListRequestSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void addressListRequestSuccess(ResponseData responseData) {
       // if()
       // mView.updateAddressList
    }

    @Override
    public void onCreate() {

    }


    @Override
    public void onDestroy() {
        mView = null;
    }
}
