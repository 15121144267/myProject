package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.entity.ClassifySearchListResponse;
import com.banshengyuan.feima.view.PresenterControl.ClassifySearchControl;
import com.banshengyuan.feima.view.model.GoodsClassifyModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/4/27.
 * PresenterLoginImpl
 */

public class PresenterClassifySearchImpl implements ClassifySearchControl.PresenterClassifySearch {

    private ClassifySearchControl.ClassifySearchView mView;
    private final GoodsClassifyModel mModel;
    private final Context mContext;


    @Inject
    public PresenterClassifySearchImpl(Context context, GoodsClassifyModel model, ClassifySearchControl.ClassifySearchView view) {
        mContext = context;
        mModel = model;
        mView = view;
    }

    @Override
    public void requestCommentList(Integer fairId, Integer page, Integer pageSize) {
        mView.showLoading("");
        Disposable disposable = mModel.commentListRequest(fairId, page, pageSize).compose(mView.applySchedulers())
                .subscribe(this::getCommentListSuccess
                        , throwable -> mView.loadError(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getCommentListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(ClassifySearchListResponse.class);
            ClassifySearchListResponse response = (ClassifySearchListResponse) responseData.parsedData;
            mView.getCommentListSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
            mView.getCommentListFail();
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
