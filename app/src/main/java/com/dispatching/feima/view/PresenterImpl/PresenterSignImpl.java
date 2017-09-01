package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.view.PresenterControl.SignControl;
import com.dispatching.feima.view.model.ResponseData;
import com.dispatching.feima.view.model.SignModel;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterSignImpl
 */

public class PresenterSignImpl implements SignControl.PresenterSign {
    private SignModel mSignModel;
    private SignControl.SignView mView;
    private Context mContext;

    @Inject
    public PresenterSignImpl(Context context, SignControl.SignView view, SignModel model) {
        mContext = context;
        mSignModel = model;
        mView = view;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onRequestVerifyCode(String phone) {
        mView.showLoading("正在获取");
        Disposable disposable = mSignModel.verityCodeRequest(phone)
                .compose(mView.applySchedulers())
                .subscribe(this::getVerifyCodeSuccess
                        , throwable -> mView.showErrMessage(throwable),() -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getVerifyCodeSuccess(ResponseData responseData) {
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(aLong -> 60 - aLong)
                .take(61)
                .compose(mView.applySchedulers())
                .subscribe(aLong -> mView.setButtonEnable(false, aLong),
                        throwable -> {},
                        () -> mView.setButtonEnable(true, (long) 0));
        if (responseData.resultCode == 100) {
            mView.showToast(mContext.getString(R.string.verity_send_success));
        }
    }

    @Override
    public void onRequestSign(String phone, String password,String verityCode ) {
        Disposable disposable = mSignModel.veritySignUp(phone, password, verityCode)
                .compose(mView.applySchedulers())
                .subscribe(this::signSuccess
                        , throwable -> mView.showErrMessage(throwable));
        mView.addSubscription(disposable);
    }

    private void signSuccess(ResponseData responseData) {
        if(responseData.resultCode == 100){
            mView.signUpSuccess();
        }else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
