package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MyCollectionBlockResponse;
import com.banshengyuan.feima.view.PresenterControl.CollectionBlockControl;
import com.banshengyuan.feima.view.model.CollectionModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddAddressImpl
 */

public class PresenterCollectionBlockImpl implements CollectionBlockControl.PresenterCollectionBlock {
    private CollectionBlockControl.CollectionBlockView mView;
    private Context mContext;
    private CollectionModel mModel;

    @Inject
    public PresenterCollectionBlockImpl(Context context, CollectionBlockControl.CollectionBlockView view, CollectionModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void requestCollectionBlockList(int page, int pageSize,String token) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.collectionBlockRequest(page, pageSize,token).compose(mView.applySchedulers())
                .subscribe(this::getCollectionBlockSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getCollectionBlockSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(MyCollectionBlockResponse.class);
            MyCollectionBlockResponse response = (MyCollectionBlockResponse) responseData.parsedData;
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
