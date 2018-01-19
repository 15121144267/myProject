package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.FairBottomResponse;
import com.banshengyuan.feima.entity.FairUnderLineResponse;
import com.banshengyuan.feima.entity.RecommendBrandResponse;
import com.banshengyuan.feima.view.PresenterControl.MainFairControl;
import com.banshengyuan.feima.view.model.MainModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/5/3.
 * PresenterPendingImpl
 */

public class PresenterMainFairImpl implements MainFairControl.PresenterFair {
    private final MainModel mMainModel;
    private MainFairControl.MainFairView mView;
    private final Context mContext;

    @Inject
    public PresenterMainFairImpl(Context context, MainModel model, MainFairControl.MainFairView view) {
        mContext = context;
        mMainModel = model;
        mView = view;
    }

    @Override
    public void requestFairBottom() {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mMainModel.requestFairBottom().compose(mView.applySchedulers())
                .subscribe(this::getFairBottomSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.getFairBottomComplete());
        mView.addSubscription(disposable);
    }

    private void getFairBottomSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(FairBottomResponse.class);
            FairBottomResponse response = (FairBottomResponse) responseData.parsedData;
            mView.getFairBottomSuccess(response);
        } else {
            mView.getFairBottomFail();
        }
    }

    @Override
    public void requestFairUnderLine(double longitude,double latitude) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mMainModel.vistaListRequest(longitude,latitude).compose(mView.applySchedulers())
                .subscribe(this::getFairUnderLineSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.getFairUnderLineComplete());
        mView.addSubscription(disposable);
    }

    private void getFairUnderLineSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(FairUnderLineResponse.class);
            FairUnderLineResponse response = (FairUnderLineResponse) responseData.parsedData;
            mView.getFairUnderLineSuccess(response);
        } else {
            mView.getFairUnderLineFail();
        }
    }

    @Override
    public void requestRecommendBrand() {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mMainModel.requestRecommendBrand().compose(mView.applySchedulers())
                .subscribe(this::getRecommendBrandSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.getRecommendBrandComplete());
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
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
