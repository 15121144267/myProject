package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.view.PresenterControl.ForgetControl;
import com.banshengyuan.feima.view.model.ForgetModel;
import com.banshengyuan.feima.view.model.ResponseData;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterForgetImpl
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
    public void requestCheckCode(String phone, String code, String password) {
        Disposable disposable = mModel.checkCodeRequest(phone, code, password)
                .compose(mView.applySchedulers())
                .subscribe(this::checkCodeSuccess
                        , throwable -> mView.showErrMessage(throwable));
        mView.addSubscription(disposable);
    }

    private void checkCodeSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            mView.checkCodeSuccess();
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void onRequestVerifyCode(String phone) {
        mView.showLoading("正在获取");
        Disposable disposable = mModel.verityCodeRequest(phone)
                .compose(mView.applySchedulers())
                .subscribe(this::getVerifyCodeSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getVerifyCodeSuccess(ResponseData responseData) {
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(aLong -> 60 - aLong)
                .take(61)
                .compose(mView.applySchedulers())
                .subscribe(aLong -> mView.setButtonEnable(false, aLong),
                        throwable -> {
                        },
                        () -> mView.setButtonEnable(true, (long) 0));
        if (responseData.resultCode == 200) {
            mView.showToast(mContext.getString(R.string.verity_send_success));
        } else {
            mView.showToast("请稍后重试");
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
