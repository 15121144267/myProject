package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.ShopListResponse;
import com.dispatching.feima.view.PresenterControl.ShopListControl;
import com.dispatching.feima.view.model.ResponseData;
import com.dispatching.feima.view.model.ShopListModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 */

public class PresenterShopListImpl implements ShopListControl.PresenterShopList {
    private Context mContext;
    private ShopListControl.ShopListView mView;
    private ShopListModel mModel;

    @Inject
    public PresenterShopListImpl(Context context, ShopListControl.ShopListView view, ShopListModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void requestShopList(Integer pagerNo, Integer pagerSize) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.shopListRequest(pagerNo, pagerSize).compose(mView.applySchedulers())
                .subscribe(this::shopListRequestSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }


    private void shopListRequestSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(ShopListResponse.class);
            ShopListResponse response = (ShopListResponse) responseData.parsedData;
            mView.getShopListSuccess(response.list);
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
