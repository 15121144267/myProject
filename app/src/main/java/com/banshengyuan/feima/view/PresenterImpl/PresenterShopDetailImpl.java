package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.ShopDetailBannerResponse;
import com.banshengyuan.feima.entity.ShopDetailResponse;
import com.banshengyuan.feima.view.PresenterControl.ShopDetailControl;
import com.banshengyuan.feima.view.model.ResponseData;
import com.banshengyuan.feima.view.model.ShopDetailModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterShopDetailImpl
 */

public class PresenterShopDetailImpl implements ShopDetailControl.PresenterShopDetail {

    private ShopDetailControl.ShopDetailView mView;
    private Context mContext;
    private ShopDetailModel mModel;
    private boolean isShow = true;

    @Inject
    public PresenterShopDetailImpl(Context context, ShopDetailControl.ShopDetailView view, ShopDetailModel model) {
        mView = view;
        mModel = model;
        mContext = context;
    }

    @Override
    public void requestShopBanner(String partnerId, String storeCode) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.shopBannerRequest(partnerId, storeCode).compose(mView.applySchedulers())
                .subscribe(this::getShopBannerSuccess
                        , throwable -> mView.loadFail(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getShopBannerSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            responseData.parseData(ShopDetailBannerResponse.class);
            ShopDetailBannerResponse response = (ShopDetailBannerResponse) responseData.parsedData;
            mView.getShopBannerSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void requestShopGoodsList(String sortName, Integer sortOrder, String storeCode, Integer pagerNumber, Integer pagerSize) {
        if (isShow) {
            isShow = false;
            mView.showLoading(mContext.getString(R.string.loading));
        }
        Disposable disposable = mModel.shopGoodsListRequest(sortName, sortOrder, storeCode, pagerNumber, pagerSize).compose(mView.applySchedulers())
                .subscribe(this::getShopGoodsListSuccess
                        , throwable -> mView.loadFail(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getShopGoodsListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            responseData.parseData(ShopDetailResponse.class);
            ShopDetailResponse response = (ShopDetailResponse) responseData.parsedData;
            mView.transformShopGoodsListSuccess(response.products);
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
