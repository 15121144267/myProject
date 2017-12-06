package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.ShopSortListResponse;
import com.banshengyuan.feima.entity.StoreCategoryListResponse;
import com.banshengyuan.feima.entity.StreetSortListResponse;
import com.banshengyuan.feima.view.PresenterControl.ShopBlockControl;
import com.banshengyuan.feima.view.model.ResponseData;
import com.banshengyuan.feima.view.model.ShopBlockModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddAddressImpl
 */

public class PresenterShopBlockImpl implements ShopBlockControl.PresenterShopBlock {
    private ShopBlockControl.ShopBlockView mView;
    private Context mContext;
    private ShopBlockModel mModel;

    @Inject
    public PresenterShopBlockImpl(Context context, ShopBlockControl.ShopBlockView view, ShopBlockModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void requestStreetSortList() {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.streetSortListRequest().compose(mView.applySchedulers())
                .subscribe(this::getStreetSortListSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getStreetSortListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(StreetSortListResponse.class);
            StreetSortListResponse response = (StreetSortListResponse) responseData.parsedData;
            mView.getStreetSortListSuccess(response);
        } else {
            mView.getStreetSortListFail(responseData.errorDesc);
        }
    }

    @Override
    public void requestShopList(Integer streetId, Integer categoryId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.shopListRequest(streetId,categoryId).compose(mView.applySchedulers())
                .subscribe(this::getShopListSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getShopListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(StoreCategoryListResponse.class);
            StoreCategoryListResponse response = (StoreCategoryListResponse) responseData.parsedData;
            mView.getShopListSuccess(response);
        } else {
            mView.getShopListFail(responseData.errorDesc);
        }
    }

    @Override
    public void requestShopSortList() {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.shopSortListRequest().compose(mView.applySchedulers())
                .subscribe(this::getShopSortListSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getShopSortListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(ShopSortListResponse.class);
            ShopSortListResponse response = (ShopSortListResponse) responseData.parsedData;
            mView.getShopSortListSuccess(response);
        } else {
            mView.getShopSortListFail(responseData.errorDesc);
        }
    }
    /*@Override
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
