package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MyOrdersResponse;
import com.banshengyuan.feima.help.RetryWithDelay;
import com.banshengyuan.feima.view.PresenterControl.WaitPayControl;
import com.banshengyuan.feima.view.model.MyOrderModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterWaitPayImpl
 */

public class PresenterWaitPayImpl implements WaitPayControl.PresenterWaitPay {
    private WaitPayControl.WaitPayView mView;
    private Context mContext;
    private MyOrderModel mModel;

    @Inject
    public PresenterWaitPayImpl(Context context, WaitPayControl.WaitPayView view, MyOrderModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void requestMyOrderList(Integer pageNo, Integer pageSize, String status,String token) {
        Disposable disposable = mModel.myOrderListRequest(pageNo, pageSize, status, token).retryWhen(new RetryWithDelay(10, 3000)).compose(mView.applySchedulers())
                .subscribe(this::getMyOrderListSuccess,
                        throwable -> mView.loadFail(throwable));
        mView.addSubscription(disposable);
    }

    private void getMyOrderListSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            responseData.parseData(MyOrdersResponse.class);
            MyOrdersResponse response = (MyOrdersResponse) responseData.parsedData;
            mView.getMyOrderListSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void requestCancelOrder(String order_sn, String token) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.cancelOrderRequest(order_sn, token).retryWhen(new RetryWithDelay(10, 3000)).compose(mView.applySchedulers())
                .subscribe(this::cancelOrderSuccess,
                        throwable -> mView.loadFail(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    @Override
    public void requestDeleteOrder(String order_sn, String token) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.deleteOrderRequest(order_sn, token).retryWhen(new RetryWithDelay(10, 3000)).compose(mView.applySchedulers())
                .subscribe(this::deleteOrderSuccess,
                        throwable -> mView.loadFail(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void deleteOrderSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            mView.getDeleteOrderSuccess();
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    private void cancelOrderSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            mView.getCancelOrderSuccess();
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
