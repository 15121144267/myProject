package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.ExChangeResponse;
import com.banshengyuan.feima.help.RetryWithDelay;
import com.banshengyuan.feima.view.PresenterControl.ExChangeControl;
import com.banshengyuan.feima.view.model.MainModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddAddressImpl
 */

public class PresenterExChangeImpl implements ExChangeControl.PresenterExChange {

    private ExChangeControl.ExChangeView mView;
    private Context mContext;
    private MainModel mModel;

    @Inject
    public PresenterExChangeImpl(Context context, ExChangeControl.ExChangeView view, MainModel model) {
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

    @Override
    public void requestHotFairInfo(String street_id) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.hotFairRequest(1, 10, street_id).retryWhen(new RetryWithDelay(10, 3000)).compose(mView.applySchedulers())
                .subscribe(this::getHotFairInfoSuccess
                        , throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getHotFairInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(ExChangeResponse.class);
            ExChangeResponse response = (ExChangeResponse) responseData.parsedData;
            mView.getHotFairInfoSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }
}
