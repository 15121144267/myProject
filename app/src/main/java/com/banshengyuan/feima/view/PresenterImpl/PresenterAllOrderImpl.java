package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MyOrdersResponse;
import com.banshengyuan.feima.view.PresenterControl.AllOrderControl;
import com.banshengyuan.feima.view.model.MyOrderModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAllOrderImpl
 */

public class PresenterAllOrderImpl implements AllOrderControl.PresenterAllOrderView {
    private AllOrderControl.AllOrderView mView;
    private Context mContext;
    private MyOrderModel mModel;
    private boolean isShow = true;

    @Inject
    public PresenterAllOrderImpl(Context context, AllOrderControl.AllOrderView view, MyOrderModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void requestMyOrderList(Integer pageNo, Integer pageSize) {
        if (isShow) {
            isShow = false;
            mView.showLoading(mContext.getString(R.string.loading));
        }
        Disposable disposable = mModel.myOrderListRequest(pageNo, pageSize).compose(mView.applySchedulers())
                .subscribe(this::getMyOrderListSuccess,
                        throwable -> mView.loadFail(throwable),() -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getMyOrderListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            responseData.parseData(MyOrdersResponse.class);
            MyOrdersResponse response = (MyOrdersResponse) responseData.parsedData;
            mView.getMyOrderListSuccess(response);
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
