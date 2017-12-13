package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.FairContentDetailResponse;
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
    public PresenterWorkSummaryImpl(Context context , WorkSummaryModel workSummaryModel,WorkSummaryControl.WorkSummaryView view) {
        mContext = context;
        mWorkSummaryModel = workSummaryModel;
        mView = view;
    }

    @Override
    public void requestFairDetail(Integer fairId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable =  mWorkSummaryModel.fairDetailRequest(fairId).compose(mView.applySchedulers())
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
