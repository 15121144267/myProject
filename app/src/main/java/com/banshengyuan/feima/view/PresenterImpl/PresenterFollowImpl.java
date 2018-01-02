package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.FairDetailListResponse;
import com.banshengyuan.feima.view.PresenterControl.FollowControl;
import com.banshengyuan.feima.view.model.FairDetailModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/5/3.
 * PresenterPendingImpl
 */

public class PresenterFollowImpl implements FollowControl.PresenterFollow {
    private final FairDetailModel mModel;
    private FollowControl.FollowView mView;
    private final Context mContext;

    @Inject
    public PresenterFollowImpl(Context context, FairDetailModel model, FollowControl.FollowView view) {
        mContext = context;
        mModel = model;
        mView = view;
    }

    @Override
    public void requestFairList(Integer fairId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.fairListRequest(fairId).compose(mView.applySchedulers())
                .subscribe(this::getFairListSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getFairListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(FairDetailListResponse.class);
            FairDetailListResponse response = (FairDetailListResponse) responseData.parsedData;
            mView.getFairListSuccess(response);
        } else {
            mView.getFairListFail();
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
