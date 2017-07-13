package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.entity.PersonInfoResponse;
import com.dispatching.feima.view.PresenterControl.WelcomeControl;
import com.dispatching.feima.view.model.ResponseData;
import com.dispatching.feima.view.model.WelcomeModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/4/27.
 * PresenterLoginImpl
 */

public class PresenterWelcomeImpl implements WelcomeControl.PresenterWelcome {

    private WelcomeControl.WelcomeView mView;
    private final WelcomeModel mModel;
    private final Context mContext;

    @Inject
    public PresenterWelcomeImpl(Context context, WelcomeModel model, WelcomeControl.WelcomeView view) {
        mContext = context;
        mModel = model;
        mView = view;
    }

    @Override
    public void requestPersonInfo(String phone) {
        Disposable disposable = mModel.personInfoRequest(phone).compose(mView.applySchedulers())
                .subscribe(this::getPersonInfoSuccess
                        , throwable -> mView.showErrMessage(throwable));
        mView.addSubscription(disposable);
    }

    private void getPersonInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
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
