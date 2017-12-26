package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.ProductCategoryResponse;
import com.banshengyuan.feima.view.PresenterControl.ProductListControl;
import com.banshengyuan.feima.view.model.ProductListModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddAddressImpl
 */

public class PresenterProductListImpl implements ProductListControl.PresenterProductList {
    private ProductListControl.ProductListView mView;
    private Context mContext;
    private ProductListModel mModel;

    @Inject
    public PresenterProductListImpl(Context context, ProductListControl.ProductListView view, ProductListModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void requestProductList(Integer categoryId,Integer page,Integer pageSize) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.productListRequest(categoryId,page,pageSize).compose(mView.applySchedulers())
                .subscribe(this::getProductListSuccess, throwable -> mView.loadError(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getProductListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(ProductCategoryResponse.class);
            ProductCategoryResponse response = (ProductCategoryResponse) responseData.parsedData;
            mView.getProductListSuccess(response);
        } else {
            mView.getProductListFail(responseData.errorDesc);
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
