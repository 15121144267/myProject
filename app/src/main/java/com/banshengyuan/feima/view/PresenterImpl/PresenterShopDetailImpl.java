package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.GoodsCommentResponse;
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

    @Inject
    public PresenterShopDetailImpl(Context context, ShopDetailControl.ShopDetailView view, ShopDetailModel model) {
        mView = view;
        mModel = model;
        mContext = context;
    }

    @Override
    public void requestGoodsComment(Integer goodsId, Integer page, Integer pageSize) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.goodsCommentRequest(goodsId, page, pageSize).compose(mView.applySchedulers())
                .subscribe(this::getGoodsCommentSuccess
                        , throwable -> mView.loadError(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getGoodsCommentSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(GoodsCommentResponse.class);
            GoodsCommentResponse response = (GoodsCommentResponse) responseData.parsedData;
            mView.getGoodsCommentSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
            mView.loadFail();
        }
    }
    /* @Override
    public void requestShopBanner(String partnerId, String storeCode) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.shopBannerRequest(partnerId, storeCode).compose(mView.applySchedulers())
                .subscribe(this::getShopBannerSuccess
                        , throwable -> mView.loadFail(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getShopBannerSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            responseData.parseData(GoodsCommentResponse.class);
            GoodsCommentResponse response = (GoodsCommentResponse) responseData.parsedData;
            mView.getShopBannerSuccess(response);
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
