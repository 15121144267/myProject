package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.CollectionResponse;
import com.banshengyuan.feima.entity.FairContentDetailResponse;
import com.banshengyuan.feima.entity.FairPraiseResponse;
import com.banshengyuan.feima.view.PresenterControl.WorkSummaryControl;
import com.banshengyuan.feima.view.model.ResponseData;
import com.banshengyuan.feima.view.model.WorkSummaryModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/5/17.
 * PresenterWorkSummaryImpl
 */

public class PresenterWorkSummaryImpl implements WorkSummaryControl.PresenterWorkSummary {
    private WorkSummaryControl.WorkSummaryView mView;
    private final Context mContext;
    private final WorkSummaryModel mWorkSummaryModel;

    @Inject
    public PresenterWorkSummaryImpl(Context context, WorkSummaryModel workSummaryModel, WorkSummaryControl.WorkSummaryView view) {
        mContext = context;
        mWorkSummaryModel = workSummaryModel;
        mView = view;
    }

    @Override
    public void requestPraise(Integer fairId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mWorkSummaryModel.fairPraiseRequest(fairId).compose(mView.applySchedulers())
                .subscribe(this::getFairPraiseSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getFairPraiseSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            responseData.parseData(FairPraiseResponse.class);
            FairPraiseResponse response = (FairPraiseResponse) responseData.parsedData;
            mView.getFairPraiseSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void requestFairCollection(Integer fairId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mWorkSummaryModel.fairCollectionRequest(fairId).compose(mView.applySchedulers())
                .subscribe(this::getFairCollectionSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getFairCollectionSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            responseData.parseData(CollectionResponse.class);
            CollectionResponse response = (CollectionResponse) responseData.parsedData;
            mView.getFairCollectionSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void requestGoodCollection(Integer goodId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mWorkSummaryModel.goodCollectionRequest(goodId).compose(mView.applySchedulers())
                .subscribe(this::getGoodCollectionSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getGoodCollectionSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            responseData.parseData(CollectionResponse.class);
            CollectionResponse response = (CollectionResponse) responseData.parsedData;
            mView.getGoodCollectionSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void requestFairDetail(Integer fairId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mWorkSummaryModel.fairDetailRequest(fairId).compose(mView.applySchedulers())
                .subscribe(this::getFairDetailSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getFairDetailSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(FairContentDetailResponse.class);
            FairContentDetailResponse response = (FairContentDetailResponse) responseData.parsedData;
            mView.getFairDetailSuccess(response);
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
