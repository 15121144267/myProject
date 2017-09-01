package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.entity.ClassifySearchListResponse;
import com.dispatching.feima.view.PresenterControl.ClassifySearchControl;
import com.dispatching.feima.view.model.GoodsClassifyModel;
import com.dispatching.feima.view.model.ResponseData;

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
    private Integer mSearchType;

    @Inject
    public PresenterClassifySearchImpl(Context context, GoodsClassifyModel model, ClassifySearchControl.ClassifySearchView view) {
        mContext = context;
        mModel = model;
        mView = view;
    }

    @Override
    public void requestClassifySearchRequest(String shopId, String nodeId, Integer deep, String sortName, Integer sortOrder, Integer searchType, Integer pageSize, Integer pageNumber) {
        mSearchType = searchType;
        Disposable disposable = mModel.sortListRequest(shopId, nodeId, deep, sortName, sortOrder, pageSize, pageNumber).compose(mView.applySchedulers())
                .subscribe(this::getProductListSuccess
                        , throwable -> mView.showErrMessage(throwable));
        mView.addSubscription(disposable);
    }


    private void getProductListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            responseData.parseData(ClassifySearchListResponse.class);
            ClassifySearchListResponse response = (ClassifySearchListResponse) responseData.parsedData;
            mView.getProductListSuccess(response);
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
