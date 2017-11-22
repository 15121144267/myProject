package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.BlockDetailResponse;
import com.banshengyuan.feima.entity.BlockHotListResponse;
import com.banshengyuan.feima.view.PresenterControl.BlockDetailControl;
import com.banshengyuan.feima.view.model.BlockDetailModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddressImpl
 */

public class PresenterBlockDetailImpl implements BlockDetailControl.PresenterBlockDetail {
    private BlockDetailControl.BlockDetailView mView;
    private Context mContext;
    private BlockDetailModel mModel;

    @Inject
    public PresenterBlockDetailImpl(Context context, BlockDetailControl.BlockDetailView view, BlockDetailModel model) {
        mContext = context;
        mView = view;
        mModel = model;
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

    @Override
    public void requestBlockDetail(Integer blockId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.blockDetailRequest(blockId).compose(mView.applySchedulers())
                .subscribe(this::getBlockDetailSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getBlockDetailSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(BlockDetailResponse.class);
            BlockDetailResponse response = (BlockDetailResponse) responseData.parsedData;
            mView.getBlockDetailSuccess(response);
        } else {
            mView.getBlockDetailFail();
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
