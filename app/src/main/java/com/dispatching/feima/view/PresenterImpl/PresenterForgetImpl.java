package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.view.PresenterControl.ForgetControl;
import com.dispatching.feima.view.model.ForgetModel;
import com.dispatching.feima.view.model.ResponseData;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 */

public class PresenterForgetImpl implements ForgetControl.PresenterForget {
    private Context mContext;
    private ForgetControl.ForgetView mView;
    private ForgetModel mModel;

    @Inject
    public PresenterForgetImpl(Context context, ForgetControl.ForgetView view, ForgetModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void requestCheckCode(String phone, String code) {
        Disposable disposable = mModel.checkCodeRequest(phone, code)
                .compose(mView.applySchedulers())
                .subscribe(this::checkCodeSuccess
                        , throwable -> mView.showErrMessage(throwable));
        mView.addSubscription(disposable);
    }

    @Override
    public void onRequestVerifyCode(String phone) {
        Disposable disposable = mModel.verityCodeRequest(phone)
                .compose(mView.applySchedulers())
                .subscribe(this::getVerifyCodeSuccess
                        , throwable -> mView.showErrMessage(throwable));
        mView.addSubscription(disposable);
    }

    private void checkCodeSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            mView.checkCodeSuccess();
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    private void getVerifyCodeSuccess(ResponseData responseData) {
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(aLong -> 59 - aLong)
                .take(60)
                .compose(mView.applySchedulers())
                .subscribe(aLong -> mView.setButtonEnable(false, aLong),
                        throwable -> {
                        },
                        () -> mView.setButtonEnable(true, (long) 0));
        if (responseData.resultCode == 100) {
            mView.showToast(mContext.getString(R.string.verity_send_success));
        }
    }

    @Override
    public void onDestroy() {

    }
}
