package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.FairCategoryResponse;
import com.banshengyuan.feima.view.PresenterControl.FairDetailControl;
import com.banshengyuan.feima.view.model.FairDetailModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

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

    @Override
    public void requestCategoryInfo(Integer fairCategoryId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.categoryInfoRequest(fairCategoryId).compose(mView.applySchedulers())
                .subscribe(this::getCategoryInfoSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getCategoryInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(FairCategoryResponse.class);
            FairCategoryResponse response = (FairCategoryResponse) responseData.parsedData;
            mView.getCategoryInfoSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
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
