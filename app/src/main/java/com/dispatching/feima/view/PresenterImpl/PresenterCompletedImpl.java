package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.PersonInfoResponse;
import com.dispatching.feima.view.PresenterControl.CompletedOrderControl;
import com.dispatching.feima.view.model.MainModel;
import com.dispatching.feima.view.model.ResponseData;

import javax.inject.Inject;

/**
 * Created by helei on 2017/5/3.
 * PresenterCompletedImpl
 */

public class PresenterCompletedImpl implements CompletedOrderControl.PresenterCompletedOrder {
    private final MainModel mMainModel;
    private CompletedOrderControl.CompletedOrderView mView;
    private final Context mContext;

    @Inject
    public PresenterCompletedImpl(Context context, MainModel model,CompletedOrderControl.CompletedOrderView view) {
        mContext = context;
        mMainModel = model;
        mView = view;
    }

    @Override
    public void requestPersonInfo(String phone) {
        mView.showLoading(mContext.getString(R.string.loading));
        mMainModel.personInfoRequest(phone).compose(mView.applySchedulers())
                .subscribe(this::getPersonInfoSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
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
