package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.AllFairListResponse;
import com.banshengyuan.feima.view.PresenterControl.GoodsClassifyControl;
import com.banshengyuan.feima.view.model.GoodsClassifyModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterGoodsClassifyImpl
 */

public class PresenterGoodsClassifyImpl implements GoodsClassifyControl.PresenterGoodsClassify {
    private GoodsClassifyControl.GoodsClassifyView mView;
    private Context mContext;
    private GoodsClassifyModel mModel;

    @Inject
    public PresenterGoodsClassifyImpl(Context context, GoodsClassifyControl.GoodsClassifyView view, GoodsClassifyModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void requestAllFairList() {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.allFairListRequest().compose(mView.applySchedulers())
                .subscribe(this::getAllFairListSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getAllFairListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(AllFairListResponse.class);
            AllFairListResponse response = (AllFairListResponse) responseData.parsedData;
            mView.getAllFairListSuccess(response);
        } else {
            mView.getAllFairListFail();
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
