package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.module.ShoppingCardListResponse;
import com.dispatching.feima.view.PresenterControl.ShoppingCardControl;
import com.dispatching.feima.view.model.ResponseData;
import com.dispatching.feima.view.model.ShoppingCardModel;

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
    public void requestChangeProductNumber(String shoppingCardId, String productId, String productCount) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mShoppingCardModel.changeProductNumberRequest(shoppingCardId, productId, productCount).compose(mView.applySchedulers())
                .subscribe(this::changeProductNumberSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void changeProductNumberSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            mView.changeProductNumberSuccess();
        } else {
            mView.showToast(responseData.errorDesc);
        }

    }

    @Override
    public void requestDeleteProduct(String shoppingCardId, String productId, String productCount) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mShoppingCardModel.deleteProductRequest(shoppingCardId, productId, productCount).compose(mView.applySchedulers())
                .subscribe(this::deleteProductSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void deleteProductSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            mView.deleteProductSuccess();
        } else {
            mView.showToast(responseData.errorDesc);
        }

    }

    @Override
    public void requestShoppingCardList(String companyId, String userId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mShoppingCardModel.shoppingCardListRequest(companyId, userId).compose(mView.applySchedulers())
                .subscribe(this::shoppingCardListSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void shoppingCardListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            responseData.parseData(ShoppingCardListResponse.class);
            ShoppingCardListResponse response = (ShoppingCardListResponse) responseData.parsedData;
            mView.shoppingCardListSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }

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
