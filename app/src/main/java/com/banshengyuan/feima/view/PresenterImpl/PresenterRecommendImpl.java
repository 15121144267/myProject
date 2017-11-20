package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.RecommendBottomResponse;
import com.banshengyuan.feima.entity.RecommendBrandResponse;
import com.banshengyuan.feima.entity.RecommendTopResponse;
import com.banshengyuan.feima.view.PresenterControl.RecommendControl;
import com.banshengyuan.feima.view.model.MainModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/5/3.
 * PresenterPendingImpl
 */

public class PresenterRecommendImpl implements RecommendControl.PresenterRecommend {
    private final MainModel mMainModel;
    private RecommendControl.RecommendView mView;
    private final Context mContext;

    @Inject
    public PresenterRecommendImpl(Context context, MainModel model, RecommendControl.RecommendView view) {
        mContext = context;
        mMainModel = model;
        mView = view;
    }

    @Override
    public void requestRecommendBottom() {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mMainModel.requestRecommendBottom().compose(mView.applySchedulers())
                .subscribe(this::getRecommendBottomSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getRecommendBottomSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(RecommendBottomResponse.class);
            RecommendBottomResponse response = (RecommendBottomResponse) responseData.parsedData;
            mView.getRecommendBottomSuccess(response);
        } else {
            mView.getRecommendBottomFail();
        }
    }

    @Override
    public void requestRecommendBrand() {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mMainModel.requestRecommendBrand().compose(mView.applySchedulers())
                .subscribe(this::getRecommendBrandSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getRecommendBrandSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(RecommendBrandResponse.class);
            RecommendBrandResponse response = (RecommendBrandResponse) responseData.parsedData;
            mView.getRecommendBrandSuccess(response);
        } else {
            mView.getRecommendBrandFail();
        }
    }

    @Override
    public void requestRecommendTop(double longitude, double latitude) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mMainModel.recommendTopRequest(longitude, latitude).compose(mView.applySchedulers())
                .subscribe(this::getRecommendTopSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getRecommendTopSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(RecommendTopResponse.class);
            RecommendTopResponse response = (RecommendTopResponse) responseData.parsedData;
            mView.getRecommendTopSuccess(response);
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
