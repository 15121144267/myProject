package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.GoodsInfoResponse;
import com.dispatching.feima.entity.SpecificationResponse;
import com.dispatching.feima.view.PresenterControl.GoodsDetailControl;
import com.dispatching.feima.view.model.GoodsDetailModel;
import com.dispatching.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/4/28.
 * PresenterOrderDetailImpl
 */

public class PresenterGoodsDetailImpl implements GoodsDetailControl.PresenterGoodsDetail {

    private GoodsDetailControl.GoodsDetailView mView;
    private final Context mContext;
    private final GoodsDetailModel mModel;

    @Inject
    public PresenterGoodsDetailImpl(Context context, GoodsDetailModel model, GoodsDetailControl.GoodsDetailView view) {
        mContext =context;
        mModel = model;
        mView = view;
    }

    @Override
    public void requestGoodInfo(String productId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.goodInfoRequest(productId)
                .compose(mView.applySchedulers())
                .subscribe(this::goodInfoSuccess
                        , throwable -> mView.showErrMessage(throwable),() -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }


    private void goodInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            responseData.parseData(GoodsInfoResponse.class);
            GoodsInfoResponse response = (GoodsInfoResponse) responseData.parsedData;
            mView.getGoodsInfoSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void requestGoodsSpecification(String productId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.goodInfoSpecificationRequest(productId)
                .compose(mView.applySchedulers())
                .subscribe(this::goodInfoSpecificationSuccess
                        , throwable -> mView.showErrMessage(throwable),() -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void goodInfoSpecificationSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            responseData.parseData(SpecificationResponse.class);
            SpecificationResponse response = (SpecificationResponse) responseData.parsedData;
            mView.goodInfoSpecificationSuccess(response);
        } else {
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
