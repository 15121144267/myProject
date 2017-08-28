package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.SortListResponse;
import com.dispatching.feima.view.PresenterControl.GoodsClassifyControl;
import com.dispatching.feima.view.model.GoodsClassifyModel;
import com.dispatching.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
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
    public void requestSortList(String shopId, String nodeId, Integer deep, String sortName, Integer sortOrder) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.sortListRequest(shopId, nodeId, deep, sortName, sortOrder).compose(mView.applySchedulers())
                .subscribe(this::sortListSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void sortListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            responseData.parseData(SortListResponse.class);
            SortListResponse response = (SortListResponse) responseData.parsedData;
            mView.sortListSuccess(response);
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
