package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.view.PresenterControl.SearchControl;
import com.dispatching.feima.view.model.SearchModel;

import javax.inject.Inject;

/**
 * Created by helei on 2017/4/27.
 * PresenterLoginImpl
 */

public class PresenterSearchImpl implements SearchControl.PresenterSearch {

    private SearchControl.SearchView mView;
    private final SearchModel mModel;
    private final Context mContext;

    @Inject
    public PresenterSearchImpl(Context context, SearchModel model, SearchControl.SearchView view) {
        mContext = context;
        mModel = model;
        mView = view;
    }

    /*@Override
    public void requestPersonInfo(String phone) {
        Disposable disposable = mModel.personInfoRequest(phone).retryWhen(new RetryWithDelay(3,3000)).compose(mView.applySchedulers())
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
    }*/

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }


}
