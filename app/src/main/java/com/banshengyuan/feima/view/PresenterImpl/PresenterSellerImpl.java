package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.FairUnderLineResponse;
import com.banshengyuan.feima.entity.StoreListResponse;
import com.banshengyuan.feima.view.PresenterControl.SellerControl;
import com.banshengyuan.feima.view.model.MainModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/5/3.
 * PresenterPendingImpl
 */

public class PresenterSellerImpl implements SellerControl.PresenterSeller {
    private final MainModel mMainModel;
    private SellerControl.SellerView mView;
    private final Context mContext;

    @Inject
    public PresenterSellerImpl(Context context, MainModel model, SellerControl.SellerView view) {
        mContext = context;
        mMainModel = model;
        mView = view;
    }

    @Override
    public void requestStoreList() {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mMainModel.storeListRequest().compose(mView.applySchedulers())
                .subscribe(this::getStoreListSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.getStoreListComplete());
        mView.addSubscription(disposable);
    }

    private void getStoreListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(StoreListResponse.class);
            StoreListResponse response = (StoreListResponse) responseData.parsedData;
            mView.getStoreListSuccess(response);
        } else {
            mView.getStoreListFail();
        }
    }

    @Override
    public void requestBlockList(double longitude, double latitude) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mMainModel.vistaListRequest(longitude, latitude).compose(mView.applySchedulers())
                .subscribe(this::getBlockListSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.getBlockListComplete());
        mView.addSubscription(disposable);
    }

    private void getBlockListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(FairUnderLineResponse.class);
            FairUnderLineResponse response = (FairUnderLineResponse) responseData.parsedData;
            mView.getBlockListSuccess(response);
        } else {
            mView.getBlockListFail();
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
