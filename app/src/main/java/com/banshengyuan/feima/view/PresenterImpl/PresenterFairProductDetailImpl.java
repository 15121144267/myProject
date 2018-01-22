package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.FairDetailCollectionResponse;
import com.banshengyuan.feima.entity.HotFairDetailResponse;
import com.banshengyuan.feima.entity.HotFairStateResponse;
import com.banshengyuan.feima.entity.OrderConfirmedResponse;
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

    @Override
    public void onCreate() {
    }


    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void requestHotFairDetail(String id, String token) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.hotFairDetailRequest(id, token).retryWhen(new RetryWithDelay(10, 3000)).compose(mView.applySchedulers())
                .subscribe(this::getHotFairDetailSuccess
                        , throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }


    @Override
    public void requestHotFairState(String id, String order_sn, String token) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.hotFairStateRequest(id, order_sn, token).retryWhen(new RetryWithDelay(10, 3000)).compose(mView.applySchedulers())
                .subscribe(this::getHotFairStateSuccess
                        , throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }


    @Override
    public void requestHotFairJoinAction(String id, String phone, String token) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.hotFairJoinActionRequest(id, phone, token).compose(mView.applySchedulers())
                .subscribe(this::getHotFairJoinActionSuccess
                        , throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    @Override
    public void requestHotFairCollection(String id, String token) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.hotFairCollectionRequest(id, token).compose(mView.applySchedulers())
                .subscribe(this::getHotFairCollectionSuccess
                        , throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getHotFairCollectionSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            responseData.parseData(FairDetailCollectionResponse.class);
            FairDetailCollectionResponse response = (FairDetailCollectionResponse) responseData.parsedData;
            mView.getHotFairCollectionSuccess(response.getStatus());
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    private void getHotFairDetailSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            responseData.parseData(HotFairDetailResponse.class);
            HotFairDetailResponse response = (HotFairDetailResponse) responseData.parsedData;
            mView.getHotFairDetailSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    private void getHotFairStateSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        LogUtils.i("responseData="+responseData);
        LogUtils.i("responseData.resultCode="+responseData.resultCode);
        LogUtils.i("responseData.errorDesc="+responseData.errorDesc);
        if (responseData.resultCode == 200) {
            responseData.parseData(HotFairStateResponse.class);
            HotFairStateResponse response = (HotFairStateResponse) responseData.parsedData;
            mView.getHotFairStateSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    private void getHotFairJoinActionSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            responseData.parseData(OrderConfirmedResponse.class);
            OrderConfirmedResponse response = (OrderConfirmedResponse) responseData.parsedData;
            mView.getHotFairJoinActionSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }
}
