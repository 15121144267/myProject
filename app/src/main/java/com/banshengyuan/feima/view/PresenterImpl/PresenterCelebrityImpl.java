package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.FairDetailStoreListResponse;
import com.banshengyuan.feima.view.PresenterControl.CelebrityControl;
import com.banshengyuan.feima.view.model.FairDetailModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/5/3.
 * PresenterPendingImpl
 */

public class PresenterCelebrityImpl implements CelebrityControl.PresenterCelebrity {
    private final FairDetailModel mModel;
    private CelebrityControl.CelebrityView mView;
    private final Context mContext;

    @Inject
    public PresenterCelebrityImpl(Context context, FairDetailModel model, CelebrityControl.CelebrityView view) {
        mContext = context;
        mModel = model;
        mView = view;
    }

    @Override
    public void requestStoreList(Integer fairId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.storeListRequest(fairId).compose(mView.applySchedulers())
                .subscribe(this::getStoreListSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getStoreListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(FairDetailStoreListResponse.class);
            FairDetailStoreListResponse response = (FairDetailStoreListResponse) responseData.parsedData;
            mView.getStoreListSuccess(response);
        } else {
            mView.getStoreListFail();
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
