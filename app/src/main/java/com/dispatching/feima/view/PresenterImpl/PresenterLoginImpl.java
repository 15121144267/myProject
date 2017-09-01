package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;
import android.text.TextUtils;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.PersonInfoResponse;
import com.dispatching.feima.utils.SharePreferenceUtil;
import com.dispatching.feima.view.PresenterControl.LoginControl;
import com.dispatching.feima.view.model.LoginModel;
import com.dispatching.feima.view.model.ResponseData;

import javax.inject.Inject;

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
    public PresenterLoginImpl(Context context, LoginModel model, LoginControl.LoginView loginView, SharePreferenceUtil sharePreferenceUtil) {
        mContext = context;
        mLoginModel = model;
        mLoginView = loginView;
    }


    @Override
    public void onRequestLogin(String phone, String passWord) {
        mLoginView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mLoginModel.LoginRequest(phone, passWord).compose(mLoginView.applySchedulers())
                .subscribe(this::operationalData, throwable -> mLoginView.showErrMessage(throwable),
                        () -> mLoginView.dismissLoading());
        mLoginView.addSubscription(disposable);
    }

    private void operationalData(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            mLoginView.loginSuccess();
        } else {
            mLoginView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void requestPersonInfo(String phone) {
        mLoginView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mLoginModel.personInfoRequest(phone).compose(mLoginView.applySchedulers())
                .subscribe(this::getPersonInfoSuccess
                        , throwable -> mLoginView.showErrMessage(throwable), () -> mLoginView.dismissLoading());
        mLoginView.addSubscription(disposable);
    }

    private void getPersonInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            if(!TextUtils.isEmpty(responseData.result)){
                responseData.parseData(PersonInfoResponse.class);
                PersonInfoResponse response = (PersonInfoResponse) responseData.parsedData;
                mLoginView.getPersonInfoSuccess(response);
            }else {
                mLoginView.getPersonInfoSuccess(null);
            }


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
