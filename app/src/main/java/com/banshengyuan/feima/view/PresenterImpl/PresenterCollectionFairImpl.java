package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MyCollectionResponse;
import com.banshengyuan.feima.utils.LogUtils;
import com.banshengyuan.feima.view.PresenterControl.CollectionFairControl;
import com.banshengyuan.feima.view.model.CollectionModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddAddressImpl
 */

public class PresenterCollectionFairImpl implements CollectionFairControl.PresenterCollectionFair {
    private CollectionFairControl.CollectionFairView mView;
    private Context mContext;
    private CollectionModel mModel;

    @Inject
    public PresenterCollectionFairImpl(Context context, CollectionFairControl.CollectionFairView view, CollectionModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

   /* @Override
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

    @Override
    public void requestCollectionFairList(int page, int pageSize) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.collectionFairRequest(page, pageSize).compose(mView.applySchedulers())
                .subscribe(this::getCollectionFairSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getCollectionFairSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(MyCollectionResponse.class);
            MyCollectionResponse response = (MyCollectionResponse) responseData.parsedData;
            mView.getMyCollectionListSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }
}
