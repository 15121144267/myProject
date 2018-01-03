package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.CollectionResponse;
import com.banshengyuan.feima.entity.ShopDetailCouponListResponse;
import com.banshengyuan.feima.entity.ShopDetailProductListResponse;
import com.banshengyuan.feima.entity.StoreDetailResponse;
import com.banshengyuan.feima.view.PresenterControl.ShopProductDetailControl;
import com.banshengyuan.feima.view.model.ResponseData;
import com.banshengyuan.feima.view.model.ShopProductDetailModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddAddressImpl
 */

public class PresenterShopProductDetailImpl implements ShopProductDetailControl.PresenterShopProductDetail {
    private ShopProductDetailControl.ShopProductDetailView mView;
    private Context mContext;
    private ShopProductDetailModel mModel;

    @Inject
    public PresenterShopProductDetailImpl(Context context, ShopProductDetailControl.ShopProductDetailView view, ShopProductDetailModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void requestCollection(String id, String type) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.collectionRequest(id, type)
                .compose(mView.applySchedulers())
                .subscribe(this::getCollectionSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getCollectionSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            responseData.parseData(CollectionResponse.class);
            CollectionResponse response = (CollectionResponse) responseData.parsedData;
            mView.getCollectionSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void requestCouponInfo(Integer couponId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.couponInfoRequest(couponId).compose(mView.applySchedulers())
                .subscribe(this::getCouponInfoSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getCouponInfoSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            mView.getCouponInfoSuccess();
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void requestStoreCouponList(Integer shopId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.storeCouponListRequest(shopId).compose(mView.applySchedulers())
                .subscribe(this::getStoreCouponListSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getStoreCouponListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(ShopDetailCouponListResponse.class);
            ShopDetailCouponListResponse response = (ShopDetailCouponListResponse) responseData.parsedData;
            mView.getStoreCouponListSuccess(response);
        } else {
            mView.getStoreCouponListFail();
        }
    }

    @Override
    public void requestStoreProductList(Integer shopId, Integer page, Integer pageSize) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.storeProductListRequest(shopId, page, pageSize).compose(mView.applySchedulers())
                .subscribe(this::getStoreProductListSuccess, throwable -> mView.loadError(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getStoreProductListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(ShopDetailProductListResponse.class);
            ShopDetailProductListResponse response = (ShopDetailProductListResponse) responseData.parsedData;
            mView.getStoreProductListSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
            mView.getStoreProductListFail();
        }
    }

    @Override
    public void requestStoreDetail(Integer shopId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.shopDetailRequest(shopId).compose(mView.applySchedulers())
                .subscribe(this::getStoreDetailSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getStoreDetailSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(StoreDetailResponse.class);
            StoreDetailResponse response = (StoreDetailResponse) responseData.parsedData;
            mView.getStoreDetailSuccess(response);
        } else {
            mView.getStoreDetailFail();
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
