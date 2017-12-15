package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.PersonInfoResponse;
import com.banshengyuan.feima.view.PresenterControl.PersonCenterControl;
import com.banshengyuan.feima.view.model.PersonCenterModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterPersonCenterImpl
 */

public class PresenterPersonCenterImpl implements PersonCenterControl.PresenterPersonCenter {
    private PersonCenterControl.PersonCenterView mView;
    private Context mContext;
    private PersonCenterModel mModel;

    @Inject
    public PresenterPersonCenterImpl(Context context, PersonCenterControl.PersonCenterView view, PersonCenterModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void requestUpdatePersonInfo(PersonInfoResponse request,String token,boolean avatalFlag) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.updatePersonInfoRequest(request,token,avatalFlag).compose(mView.applySchedulers())
                .subscribe(this::updatePersonInfoSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void updatePersonInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            mView.updatePersonInfoSuccess();
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
