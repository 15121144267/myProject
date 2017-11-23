package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.ExChangeResponse;
import com.banshengyuan.feima.entity.HotFairDetailResponse;
import com.banshengyuan.feima.entity.HotFairStateResponse;
import com.banshengyuan.feima.entity.HotFariJoinActionRequest;
import com.banshengyuan.feima.entity.HotFariJoinActionResponse;
import com.banshengyuan.feima.entity.HotFariStateRequest;
import com.banshengyuan.feima.help.RetryWithDelay;
import com.banshengyuan.feima.utils.LogUtils;
import com.banshengyuan.feima.view.PresenterControl.FairProductDetailControl;
import com.banshengyuan.feima.view.model.FairProductDetailModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddAddressImpl
 */

public class PresenterFairProductDetailImpl implements FairProductDetailControl.PresenterFairProductDetail {
    private FairProductDetailControl.FairProductDetailView mView;
    private Context mContext;
    private FairProductDetailModel mModel;

    @Inject
    public PresenterFairProductDetailImpl(Context context, FairProductDetailControl.FairProductDetailView view, FairProductDetailModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }
/*
    @Override
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
    public void requestHotFairDetail(String id) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.hotFairDetailRequest(id,true).retryWhen(new RetryWithDelay(10, 3000)).compose(mView.applySchedulers())
                .subscribe(this::getHotFairDetailSuccess
                        , throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }


    @Override
    public void requestHotFairState(String id, HotFariStateRequest hotFariStateRequest) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.hotFairStateRequest(id,hotFariStateRequest).retryWhen(new RetryWithDelay(10, 3000)).compose(mView.applySchedulers())
                .subscribe(this::getHotFairStateSuccess
                        , throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }


    @Override
    public void requestHotFairJoinAction(String id, HotFariJoinActionRequest hotFariJoinActionRequest) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.hotFairJoinActionRequest(id,hotFariJoinActionRequest).retryWhen(new RetryWithDelay(10, 3000)).compose(mView.applySchedulers())
                .subscribe(this::getHotFairJoinActionSuccess
                        , throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getHotFairDetailSuccess(ResponseData responseData){
        if (responseData.resultCode == 200) {
            responseData.parseData(HotFairDetailResponse.class);
            HotFairDetailResponse response = (HotFairDetailResponse) responseData.parsedData;
            mView.getHotFairDetailSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    private void getHotFairStateSuccess(ResponseData responseData){
        if (responseData.resultCode == 200) {
            responseData.parseData(HotFairStateResponse.class);
            HotFairStateResponse response = (HotFairStateResponse) responseData.parsedData;
            mView.getHotFairStateSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    private void getHotFairJoinActionSuccess(ResponseData responseData){
        if (responseData.resultCode == 200) {
            responseData.parseData(HotFariJoinActionResponse.class);
            HotFariJoinActionResponse response = (HotFariJoinActionResponse) responseData.parsedData;
            mView.getHotFairJoinActionSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }
}
