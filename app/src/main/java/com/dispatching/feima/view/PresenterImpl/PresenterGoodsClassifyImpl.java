package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.view.PresenterControl.GoodsClassifyControl;
import com.dispatching.feima.view.model.GoodsClassifyModel;

import javax.inject.Inject;

/**
 * Created by lei.he on 2017/6/26.
 */

public class PresenterGoodsClassifyImpl implements GoodsClassifyControl.PresenterGoodsClassify {
    private GoodsClassifyControl.GoodsClassifyView mView;
    private Context mContext;
    private GoodsClassifyModel mModel;

    @Inject
    public PresenterGoodsClassifyImpl(Context context, GoodsClassifyControl.GoodsClassifyView view, GoodsClassifyModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

   /* @Override
    public void requestAddAddress(AddAddressRequest request) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.addAddressRequest(request).compose(mView.applySchedulers())
                .subscribe(this::addAddressSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void addAddressSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            mView.addAddressSuccess();
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
