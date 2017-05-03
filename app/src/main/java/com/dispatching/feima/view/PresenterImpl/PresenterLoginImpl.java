package com.dispatching.feima.view.PresenterImpl;

import com.dispatching.feima.entity.LoginResponse;
import com.dispatching.feima.utils.SharePreferenceUtil;
import com.dispatching.feima.view.PresenterControl.LoginControl;
import com.dispatching.feima.view.model.LoginModel;
import com.dispatching.feima.view.model.ResponseData;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by helei on 2017/4/27.
 */

public class PresenterLoginImpl implements LoginControl.PresenterLogin {
    private LoginControl.LoginView mLoginView;
    private LoginModel mLoginModel;

    @Inject
    public PresenterLoginImpl(LoginModel model, SharePreferenceUtil sharePreferenceUtil) {
        mLoginModel = model;
    }

    @Override
    public void onRequestVerifyCode(String phone) {
        Disposable disposable = mLoginModel.VerifyCodeRequest(phone).compose(mLoginView.applySchedulers())
                .subscribe(responseData -> getVerifyCodeSuccess(responseData)
                        , throwable -> showErrMessage(throwable));
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
        mLoginView.showLoading("加载中...");
        Disposable disposable = mLoginModel.LoginRequest(name, passWord).compose(mLoginView.applySchedulers())
                .subscribe(responseData ->  operationalData(responseData), throwable -> showErrMessage(throwable),
                        () -> mLoginView.dismissLoading());
        mLoginView.addSubscription(disposable);
    }

    private void operationalData(ResponseData responseData) {
        if(responseData.resultCode == 100){
            responseData.parseData(LoginResponse.class);
            LoginResponse loginRespone = (LoginResponse) responseData.parsedData;
            mLoginView.loginSuccess(loginRespone);
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

    private void showErrMessage(Throwable e) {
        mLoginView.dismissLoading();
        if (e instanceof HttpException || e instanceof ConnectException
                || e instanceof RuntimeException) {
            mLoginView.showToast("请检查网络");
        }
    }
}
