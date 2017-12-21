package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MyOrdersResponse;
import com.banshengyuan.feima.help.RetryWithDelay;
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
    public void requestMyOrderList(Integer pageNo, Integer pageSize, String search_status, boolean flag, String token) {
        if (isShow) {
            isShow = false;
            mView.showLoading(mContext.getString(R.string.loading));
        }
        Disposable disposable = mModel.myOrderListRequest(pageNo, pageSize, search_status, flag, token).retryWhen(new RetryWithDelay(10, 3000)).compose(mView.applySchedulers())
                .subscribe(this::getMyOrderListSuccess,
                        throwable -> mView.loadFail(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
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
    public void requestConfirmOrder(String order_sn, String token) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.comfirmOrderRequest(order_sn, token).retryWhen(new RetryWithDelay(10, 3000)).compose(mView.applySchedulers())
                .subscribe(this::comfirmOrderSuccess,
                        throwable -> mView.loadFail(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    @Override
    public void requestRemindSendGoods(String order_sn, String token) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.remindSendGoodsRequest(order_sn, token).retryWhen(new RetryWithDelay(10, 3000)).compose(mView.applySchedulers())
                .subscribe(this::remindSendGoodsSuccess,
                        throwable -> mView.loadFail(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void cancelOrderSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            mView.getCancelOrderSuccess(true);
        } else {
            mView.showToast("操作失败");
        }
    }

    private void comfirmOrderSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            mView.getComfirmOrderSuccess(true);
        } else {
            mView.showToast("操作失败");
        }
    }

    private void remindSendGoodsSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            mView.showToast("提醒成功");
        } else {
            mView.showToast("操作失败");
        }
    }

    private void getMyOrderListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
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
