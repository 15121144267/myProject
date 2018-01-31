package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.ShoppingCardListResponse;
import com.banshengyuan.feima.entity.ShoppingCardNumberResponse;
import com.banshengyuan.feima.view.PresenterControl.ShoppingCardControl;
import com.banshengyuan.feima.view.model.ResponseData;
import com.banshengyuan.feima.view.model.ShoppingCardModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/5/3.
 * PresenterPendingImpl
 */

public class PresenterShoppingCardImpl implements ShoppingCardControl.PresenterShoppingCard {
    private final ShoppingCardModel mShoppingCardModel;
    private ShoppingCardControl.ShoppingCardView mView;
    private final Context mContext;

    @Inject
    public PresenterShoppingCardImpl(Context context, ShoppingCardModel model, ShoppingCardControl.ShoppingCardView view) {
        mContext = context;
        mShoppingCardModel = model;
        mView = view;
    }

    @Override
    public void requestChangeProductNumber(Integer productId, String sku, Integer number) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mShoppingCardModel.changeProductNumberRequest(productId, sku, number).compose(mView.applySchedulers())
                .subscribe(this::changeProductNumberSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void changeProductNumberSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200 || responseData.resultCode == 300010) {
            responseData.parseData(ShoppingCardNumberResponse.class);
            ShoppingCardNumberResponse response = (ShoppingCardNumberResponse) responseData.parsedData;
            if (responseData.resultCode == 200) {
                mView.changeProductNumberSuccess(true, response);
            } else {
                mView.showToast(responseData.errorDesc);
                mView.changeProductNumberSuccess(false, response);
            }
        } else {
            mView.changeProductNumberFail(responseData.errorDesc);
        }
    }

    @Override
    public void requestDeleteProduct(Integer productId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mShoppingCardModel.deleteProductRequest(productId).compose(mView.applySchedulers())
                .subscribe(this::deleteProductSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void deleteProductSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            mView.deleteProductSuccess();
        } else {
            mView.showToast(responseData.errorDesc);
        }

    }

    @Override
    public void requestShoppingCardList() {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mShoppingCardModel.shoppingCardListRequest().compose(mView.applySchedulers())
                .subscribe(this::shoppingCardListSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.completeLoading());
        mView.addSubscription(disposable);
    }

    private void shoppingCardListSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            responseData.parseData(ShoppingCardListResponse.class);
            ShoppingCardListResponse response = (ShoppingCardListResponse) responseData.parsedData;
            mView.shoppingCardListSuccess(response);
        } else {
            mView.shoppingCardListFail(responseData.errorDesc);
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
