package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.view.PresenterControl.CollectionProductControl;
import com.banshengyuan.feima.view.model.CollectionModel;

import javax.inject.Inject;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddAddressImpl
 */

public class PresenterCollectionProductImpl implements CollectionProductControl.PresenterCollectionProduct {
    private CollectionProductControl.CollectionProductView mView;
    private Context mContext;
    private CollectionModel mModel;

    @Inject
    public PresenterCollectionProductImpl(Context context, CollectionProductControl.CollectionProductView view, CollectionModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

   /* @Override
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
