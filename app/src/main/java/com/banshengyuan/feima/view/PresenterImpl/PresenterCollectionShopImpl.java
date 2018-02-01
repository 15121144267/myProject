package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.CollectionShopResponse;
import com.banshengyuan.feima.view.PresenterControl.CollectionShopControl;
import com.banshengyuan.feima.view.model.CollectionModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddAddressImpl
 */

public class PresenterCollectionShopImpl implements CollectionShopControl.PresenterCollectionShop {
    private CollectionShopControl.CollectionShopView mView;
    private Context mContext;
    private CollectionModel mModel;
    private boolean isShow =true;

    @Inject
    public PresenterCollectionShopImpl(Context context, CollectionShopControl.CollectionShopView view, CollectionModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }


    @Override
    public void requestCollectionShopList(int page, int pageSize,String token) {
        if (isShow) {
            isShow = false;
            mView.showLoading(mContext.getString(R.string.loading));
        }
        Disposable disposable = mModel.collectionShopRequest(page, pageSize,token).compose(mView.applySchedulers())
                .subscribe(this::getCollectionShopSuccess, throwable -> mView.loadFail(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getCollectionShopSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            responseData.parseData(CollectionShopResponse.class);
            CollectionShopResponse response = (CollectionShopResponse) responseData.parsedData;
            mView.getMyCollectionListSuccess(response);
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
