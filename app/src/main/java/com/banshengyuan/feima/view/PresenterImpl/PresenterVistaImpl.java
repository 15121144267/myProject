package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.view.PresenterControl.VistaControl;
import com.banshengyuan.feima.view.model.MainModel;

import javax.inject.Inject;

/**
 * Created by helei on 2017/5/3.
 * PresenterPendingImpl
 */

public class PresenterVistaImpl implements VistaControl.PresenterVista {
    private final MainModel mMainModel;
    private VistaControl.VistaView mView;
    private final Context mContext;

    @Inject
    public PresenterVistaImpl(Context context, MainModel model, VistaControl.VistaView view) {
        mContext = context;
        mMainModel = model;
        mView = view;
    }

   /* @Override
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
    }
*/
    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
