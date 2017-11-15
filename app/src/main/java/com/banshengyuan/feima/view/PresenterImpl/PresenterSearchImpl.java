package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.view.PresenterControl.SearchControl;
import com.banshengyuan.feima.view.model.SearchModel;

import javax.inject.Inject;

/**
 * Created by helei on 2017/4/27.
 * PresenterLoginImpl
 */

public class PresenterSearchImpl implements SearchControl.PresenterSearch {

    private SearchControl.SearchView mView;
    private final SearchModel mModel;
    private final Context mContext;
    private boolean isShow = true;

    @Inject
    public PresenterSearchImpl(Context context, SearchModel model, SearchControl.SearchView view) {
        mContext = context;
        mModel = model;
        mView = view;
    }

    /*@Override
    public void requestShopList(String partnerId, String searchName) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.requestShopList(partnerId, searchName).compose(mView.applySchedulers())
                .subscribe(this::getShopListSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getShopListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            responseData.parseData(SearchShopListResponse.class);
            SearchShopListResponse response = (SearchShopListResponse) responseData.parsedData;
            mView.getShopListSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void requestProductList(String searchName, String partnerId, String sortName, Integer sortNO, Integer pagerSize, Integer pagerNo) {
        if (isShow) {
            isShow = false;
            mView.showLoading(mContext.getString(R.string.loading));
        }
        Disposable disposable = mModel.requestProductList(searchName, partnerId, sortName, sortNO, pagerSize, pagerNo).compose(mView.applySchedulers())
                .subscribe(this::getProductListSuccess
                        , throwable -> mView.showErrMessage(throwable),() -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }


    private void getProductListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            responseData.parseData(ShopDetailResponse.class);
            ShopDetailResponse response = (ShopDetailResponse) responseData.parsedData;
            mView.getProductListSuccess(response);
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
