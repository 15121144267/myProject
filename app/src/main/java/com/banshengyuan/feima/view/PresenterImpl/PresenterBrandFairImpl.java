package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.BrandAllFairListResponse;
import com.banshengyuan.feima.view.PresenterControl.BrandFairControl;
import com.banshengyuan.feima.view.model.BrandFairModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddAddressImpl
 */

public class PresenterBrandFairImpl implements BrandFairControl.PresenterBrandFair {
    private BrandFairControl.BrandFairView mView;
    private Context mContext;
    private BrandFairModel mModel;

    @Inject
    public PresenterBrandFairImpl(Context context, BrandFairControl.BrandFairView view, BrandFairModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void requestFairList() {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.fairListRequest().compose(mView.applySchedulers())
                .subscribe(this::getFairListSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getFairListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(BrandAllFairListResponse.class);
            BrandAllFairListResponse response = (BrandAllFairListResponse) responseData.parsedData;
            mView.getFairListSuccess(response);
        } else {
            mView.getFairListFail();
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
