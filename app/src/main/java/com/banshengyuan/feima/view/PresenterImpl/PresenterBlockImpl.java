package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.BlockFairListResponse;
import com.banshengyuan.feima.entity.BlockHotListResponse;
import com.banshengyuan.feima.entity.BlockStoreListResponse;
import com.banshengyuan.feima.view.PresenterControl.BlockControl;
import com.banshengyuan.feima.view.model.BlockModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddressImpl
 */

public class PresenterBlockImpl implements BlockControl.PresenterBlock {
    private BlockControl.BlockView mView;
    private Context mContext;
    private BlockModel mModel;

    @Inject
    public PresenterBlockImpl(Context context, BlockControl.BlockView view, BlockModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void requestFairList(Integer blockId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.fairListRequest(blockId).compose(mView.applySchedulers())
                .subscribe(this::getFairListSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getFairListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(BlockFairListResponse.class);
            BlockFairListResponse response = (BlockFairListResponse) responseData.parsedData;
            mView.getFairListSuccess(response);
        } else {
            mView.getFairListFail();
        }
    }

    @Override
    public void requestStoreList(Integer blockId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.storeListRequest(blockId).compose(mView.applySchedulers())
                .subscribe(this::getStoreListSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getStoreListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(BlockStoreListResponse.class);
            BlockStoreListResponse response = (BlockStoreListResponse) responseData.parsedData;
            mView.getStoreListSuccess(response);
        } else {
            mView.getStoreListFail();
        }
    }

    @Override
    public void requestHotList(Integer blockId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.hotListRequest(blockId).compose(mView.applySchedulers())
                .subscribe(this::getHotListSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getHotListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(BlockHotListResponse.class);
            BlockHotListResponse response = (BlockHotListResponse) responseData.parsedData;
            mView.getHotListSuccess(response);
        } else {
            mView.getHotListFail();
        }
    }
    /* @Override
    public void requestDeleteAddress(AddAddressRequest request) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.deleteAddressRequest(request).compose(mView.applySchedulers())
                .subscribe(this::deleteAddressSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void deleteAddressSuccess(ResponseData responseData) {
        if(responseData.resultCode == 100){
            mView.deleteAddressSuccess();
        }else {
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
