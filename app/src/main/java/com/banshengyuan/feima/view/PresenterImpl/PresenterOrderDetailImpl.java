package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.OrderDetailResponse;
import com.banshengyuan.feima.help.RetryWithDelay;
import com.banshengyuan.feima.view.PresenterControl.OrderDetailControl;
import com.banshengyuan.feima.view.model.OrderDetailModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/4/28.
 * PresenterOrderDetailImpl
 */

public class PresenterOrderDetailImpl implements OrderDetailControl.PresenterOrderDetail {

    private OrderDetailControl.OrderDetailView mView;
    private final Context mContext;
    private final OrderDetailModel mModel;

    @Inject
    public PresenterOrderDetailImpl(Context context, OrderDetailModel model, OrderDetailControl.OrderDetailView view) {
        mContext = context;
        mModel = model;
        mView = view;
    }

    @Override
    public void requestOrderDetailInfo(String order_sn, String token) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.orderDetailInfoRequest(order_sn, token).retryWhen(new RetryWithDelay(10, 3000)).compose(mView.applySchedulers())
                .subscribe(this::getOrderDetailInfoSuccess,
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
            mView.showToast("操作失败");
        }
    }

    private void cancelOrderSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            mView.getCancelOrderSuccess();
        } else {
            mView.showToast("操作失败");
        }
    }

    private void comfirmOrderSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            mView.getComfirmOrderSuccess();
        } else {
            mView.showToast("操作失败");
        }
    }

    private void remindSendGoodsSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            mView.showToast("提醒成功");
        } else {
            mView.showToast("操作失败");
        }
    }

    private void getOrderDetailInfoSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            responseData.parseData(OrderDetailResponse.class);
            OrderDetailResponse response = (OrderDetailResponse) responseData.parsedData;
            mView.getOrderDetailInfoSuccess(response);
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
