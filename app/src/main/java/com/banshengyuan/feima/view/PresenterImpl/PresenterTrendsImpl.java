package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.view.PresenterControl.TrendsControl;
import com.banshengyuan.feima.view.model.FairDetailModel;

import javax.inject.Inject;

/**
 * Created by helei on 2017/5/3.
 * PresenterPendingImpl
 */

public class PresenterTrendsImpl implements TrendsControl.PresenterTrends {
    private final FairDetailModel mModel;
    private TrendsControl.TrendsView mView;
    private final Context mContext;

    @Inject
    public PresenterTrendsImpl(Context context, FairDetailModel model, TrendsControl.TrendsView view) {
        mContext = context;
        mModel = model;
        mView = view;
    }

    /*@Override
    public void requestShopId(String scoreCode, Integer type) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mShopListModel.shopIdRequest(scoreCode, type).compose(mView.applySchedulers())
                .subscribe(this::getShopSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getShopSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(ShopResponse.class);
            ShopResponse response = (ShopResponse) responseData.parsedData;
            mView.getShopSuccess(response);
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
