package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.SearchResultResponse;
import com.banshengyuan.feima.view.PresenterControl.SearchControl;
import com.banshengyuan.feima.view.model.ResponseData;
import com.banshengyuan.feima.view.model.SearchModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/4/27.
 * PresenterLoginImpl
 */

public class PresenterSearchImpl implements SearchControl.PresenterSearch {

    private SearchControl.SearchView mView;
    private final SearchModel mModel;
    private final Context mContext;
    private boolean isShow = true;

    @Inject
    public PresenterSearchImpl(Context context, SearchModel model, SearchControl.SearchView view) {
        mContext = context;
        mModel = model;
        mView = view;
    }

    @Override
    public void requestSearchStoreList(String searchName, String searchType) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.requestSearchList(searchName, searchType).compose(mView.applySchedulers())
                .subscribe(this::getSearchStoreListSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getSearchStoreListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(SearchResultResponse.class);
            SearchResultResponse response = (SearchResultResponse) responseData.parsedData;
            mView.getSearchListSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void requestSearchStreetList(String searchName, String searchType) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.requestSearchList(searchName, searchType).compose(mView.applySchedulers())
                .subscribe(this::getSearchStreetListSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getSearchStreetListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(SearchResultResponse.class);
            SearchResultResponse response = (SearchResultResponse) responseData.parsedData;
            mView.getSearchListSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void requestSearchProductList(String searchName, String searchType) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.requestSearchList(searchName, searchType).compose(mView.applySchedulers())
                .subscribe(this::getSearchProductListSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getSearchProductListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(SearchResultResponse.class);
            SearchResultResponse response = (SearchResultResponse) responseData.parsedData;
            mView.getSearchListSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void requestSearchFairList(String searchName, String searchType) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.requestSearchList(searchName, searchType).compose(mView.applySchedulers())
                .subscribe(this::getSearchFairListSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getSearchFairListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(SearchResultResponse.class);
            SearchResultResponse response = (SearchResultResponse) responseData.parsedData;
            mView.getSearchListSuccess(response);
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
