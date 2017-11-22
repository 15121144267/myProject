package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.FairDetailProductListResponse;
import com.banshengyuan.feima.view.PresenterControl.TrendsControl;
import com.banshengyuan.feima.view.model.FairDetailModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/5/3.
 * PresenterPendingImpl
 */

public class PresenterTrendsImpl implements TrendsControl.PresenterTrends {
    private final FairDetailModel mModel;
    private TrendsControl.TrendsView mView;
    private final Context mContext;

    @Inject
    public PresenterTrendsImpl(Context context, FairDetailModel model, TrendsControl.TrendsView view) {
        mContext = context;
        mModel = model;
        mView = view;
    }

    @Override
    public void requestProductList(Integer fairId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.productListRequest(fairId).compose(mView.applySchedulers())
                .subscribe(this::getProductListSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getProductListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(FairDetailProductListResponse.class);
            FairDetailProductListResponse response = (FairDetailProductListResponse) responseData.parsedData;
            mView.getProductListSuccess(response);
        } else {
            mView.getProductListFail();
        }
    }

    /*@Override
        public void requestShopId(String scoreCode, Integer type) {
            mView.showLoading(mContext.getString(R.string.loading));
            Disposable disposable = mShopListModel.shopIdRequest(scoreCode, type).compose(mView.applySchedulers())
                    .subscribe(this::getShopSuccess
                            , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
            mView.addSubscription(disposable);
        }

        private void getShopSuccess(ResponseData responseData) {
            if (responseData.resultCode == 0) {
                responseData.parseData(ShopResponse.class);
                ShopResponse response = (ShopResponse) responseData.parsedData;
                mView.getShopSuccess(response);
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
}
