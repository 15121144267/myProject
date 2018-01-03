package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.CollectionResponse;
import com.banshengyuan.feima.entity.GoodsInfoResponse;
import com.banshengyuan.feima.entity.SkuProductResponse;
import com.banshengyuan.feima.view.PresenterControl.GoodsDetailControl;
import com.banshengyuan.feima.view.model.GoodsDetailModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/4/28.
 * PresenterOrderDetailImpl
 */

public class PresenterGoodsDetailImpl implements GoodsDetailControl.PresenterGoodsDetail {

    private GoodsDetailControl.GoodsDetailView mView;
    private final Context mContext;
    private final GoodsDetailModel mModel;

    @Inject
    public PresenterGoodsDetailImpl(Context context, GoodsDetailModel model, GoodsDetailControl.GoodsDetailView view) {
        mContext = context;
        mModel = model;
        mView = view;
    }

    @Override
    public void requestGoodsCollection(String productId, String type) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.goodsCollectionRequest(productId, type)
                .compose(mView.applySchedulers())
                .subscribe(this::getGoodsCollectionSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getGoodsCollectionSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            responseData.parseData(CollectionResponse.class);
            CollectionResponse response = (CollectionResponse) responseData.parsedData;
            mView.getGoodsCollectionSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void requestAddShoppingCard(String productId,String sku,Integer count) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.requestAddShoppingCard(productId,sku,count)
                .compose(mView.applySchedulers())
                .subscribe(this::addShoppingCardSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void addShoppingCardSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            mView.addShoppingCardSuccess();
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void requestUniqueGoodInfo(Integer productId, String sku) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.goodInfoSpecificationRequest(productId,sku)
                .compose(mView.applySchedulers())
                .subscribe(this::getUniqueGoodInfoSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getUniqueGoodInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(SkuProductResponse.class);
            SkuProductResponse response = (SkuProductResponse) responseData.parsedData;
            mView.getUniqueGoodInfoSuccess(response);
        } else {
            mView.getUniqueGoodInfoFail(responseData.errorDesc);
        }
    }

    @Override
    public void requestGoodInfo(Integer productId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.goodInfoRequest(productId)
                .compose(mView.applySchedulers())
                .subscribe(this::goodInfoSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }


    private void goodInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(GoodsInfoResponse.class);
            GoodsInfoResponse response = (GoodsInfoResponse) responseData.parsedData;
            mView.getGoodsInfoSuccess(response);
        } else {
            mView.getGoodsInfoFail(responseData.errorDesc);
        }
    }

    @Override
    public void requestGoodsSpecification(String productId) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
