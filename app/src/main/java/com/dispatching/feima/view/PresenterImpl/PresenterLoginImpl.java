package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.LoginResponse;
import com.dispatching.feima.view.PresenterControl.LoginControl;
import com.dispatching.feima.view.model.LoginModel;
import com.dispatching.feima.view.model.ResponseData;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/4/27.
 * PresenterLoginImpl
 */

public class PresenterLoginImpl implements LoginControl.PresenterLogin {

    private LoginControl.LoginView mLoginView;
    private final LoginModel mLoginModel;
    private final Context mContext;

    @Inject
    public PresenterLoginImpl(Context context, LoginModel model,LoginControl.LoginView loginView) {
        mContext = context;
        mLoginModel = model;
        mLoginView = loginView;
    }

    @Override
    public void onRequestVerifyCode(String phone) {
        Disposable disposable = mLoginModel.VerifyCodeRequest(phone)
                .subscribe(this::getVerifyCodeSuccess
                        , throwable -> mLoginView.showErrMessage(throwable));
        mLoginView.addSubscription(disposable);
    }

    private void getVerifyCodeSuccess(Integer code) {
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(aLong -> 60 - aLong)
                .take(61)
                .compose(mLoginView.applySchedulers())
                .subscribe(aLong -> mLoginView.setButtonEnable(false, aLong),
                        throwable -> {
                        },
                        () -> mLoginView.setButtonEnable(true, (long) 0));
        if (code == 1) {
            mLoginView.showToast(mContext.getString(R.string.verity_send_success));
        }
    }

    @Override
    public void onRequestLogin(String name, String passWord) {
        mLoginView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mLoginModel.LoginRequest(name, passWord).compose(mLoginView.applySchedulers())
                .subscribe(this::operationalData, throwable -> mLoginView.showErrMessage(throwable),
                        () -> mLoginView.dismissLoading());
        mLoginView.addSubscription(disposable);
    }

    private void operationalData(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            responseData.parseData(LoginResponse.class);
            LoginResponse loginResponse = (LoginResponse) responseData.parsedData;
            mLoginView.loginSuccess(loginResponse);
        } else {
            mLoginView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mLoginView = null;
    }


}
