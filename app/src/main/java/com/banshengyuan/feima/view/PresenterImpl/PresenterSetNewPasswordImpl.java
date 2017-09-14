package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.view.PresenterControl.SetNewPasswordControl;
import com.banshengyuan.feima.view.model.NewPasswordModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterSetNewPasswordImpl
 */

public class PresenterSetNewPasswordImpl implements SetNewPasswordControl.PresenterSetNewPassword {
    private SetNewPasswordControl.SetNewPasswordView mView;
    private Context mContext;
    private NewPasswordModel mModel;

    @Inject
    public PresenterSetNewPasswordImpl(Context context, SetNewPasswordControl.SetNewPasswordView view, NewPasswordModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void onRequestForSure(String phone, String smsCode, String password) {
        Disposable disposable = mModel.setPasswordRequest(phone, smsCode, password)
                .compose(mView.applySchedulers())
                .subscribe(this::setPasswordSuccess
                        , throwable -> mView.showErrMessage(throwable));
        mView.addSubscription(disposable);
    }

    private void setPasswordSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            mView.setPasswordSuccess();
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
