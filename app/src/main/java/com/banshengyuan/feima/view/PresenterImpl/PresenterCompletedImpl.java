package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.PersonInfoResponse;
import com.banshengyuan.feima.view.PresenterControl.CompletedOrderControl;
import com.banshengyuan.feima.view.model.MainModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/5/3.
 * PresenterCompletedImpl
 */

public class PresenterCompletedImpl implements CompletedOrderControl.PresenterCompletedOrder {
    private final MainModel mMainModel;
    private CompletedOrderControl.CompletedOrderView mView;
    private final Context mContext;

    @Inject
    public PresenterCompletedImpl(Context context, MainModel model, CompletedOrderControl.CompletedOrderView view) {
        mContext = context;
        mMainModel = model;
        mView = view;
    }

    @Override
    public void requestPersonInfo(String token) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mMainModel.personInfoRequest(token).compose(mView.applySchedulers())
                .subscribe(this::getPersonInfoSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    @Override
    public void requestExitLogin(String token) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mMainModel.exitLoginRequest(token).compose(mView.applySchedulers())
                .subscribe(this::getExitLoginSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getExitLoginSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            mView.getExitLoginSuccess();
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }


    private void getPersonInfoSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            responseData.parseData(PersonInfoResponse.class);
            PersonInfoResponse response = (PersonInfoResponse) responseData.parsedData;
            mView.getPersonInfoSuccess(response);
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
