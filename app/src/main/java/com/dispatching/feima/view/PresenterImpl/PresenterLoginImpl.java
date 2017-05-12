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
    private LoginModel mLoginModel;
    private Context mContext;
    @Inject
    public PresenterLoginImpl(Context context ,LoginModel model) {
        mContext = context;
        mLoginModel = model;
    }

    @Override
    public void onRequestVerifyCode(String phone) {
        Disposable disposable = mLoginModel.VerifyCodeRequest(phone).compose(mLoginView.applySchedulers())
                .subscribe(responseData -> getVerifyCodeSuccess(responseData)
                        , throwable -> mLoginView.showErrMessage(throwable));
        mLoginView.addSubscription(disposable);
    }

    private void getVerifyCodeSuccess(ResponseData responseData) {
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(aLong -> 60-aLong)
                .take(61)
                .compose(mLoginView.applySchedulers()).subscribe(aLong ->
                mLoginView.setButtonEnable(false,aLong),throwable -> {},() ->  mLoginView.setButtonEnable(true,(long) 0));
    }

    @Override
    public void onRequestLogin(String name, String passWord) {
        mLoginView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mLoginModel.LoginRequest(name, passWord).compose(mLoginView.applySchedulers())
                .subscribe(responseData ->  operationalData(responseData), throwable -> mLoginView.showErrMessage(throwable),
                        () -> mLoginView.dismissLoading());
        mLoginView.addSubscription(disposable);
    }

    private void operationalData(ResponseData responseData) {
        if(responseData.resultCode == 100){
            responseData.parseData(LoginResponse.class);
            LoginResponse loginResponse = (LoginResponse) responseData.parsedData;
            mLoginView.loginSuccess(loginResponse);
        }else {
            mLoginView.showToast(responseData.errorDesc);
        }
    }


    @Override
    public void setView(LoginControl.LoginView loginView) {
        mLoginView = loginView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mLoginView = null;
    }


}
