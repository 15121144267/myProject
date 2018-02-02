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

    /**
     * new RetryWithDelay(10, 3000) 总共重试10次，重试间隔3000毫秒
     */
    @Override
    public void requestMyOrderList(Integer pageNo, Integer pageSize, String search_status, String token) {
        if (isShow) {
            isShow = false;
            mView.showLoading(mContext.getString(R.string.loading));
        }
        Disposable disposable = mModel.myOrderListRequest(pageNo, pageSize, search_status, token).retryWhen(new RetryWithDelay(10, 3000)).compose(mView.applySchedulers())
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

    private void comfirmOrderSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            mView.getComfirmOrderSuccess();
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    private void remindSendGoodsSuccess(ResponseData responseData) {
        mView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 200) {
            mView.showToast(mContext.getString(R.string.remind_deliver));
        } else {
            mView.showToast(responseData.errorDesc);
        }
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
    public void onCreate() {

    }


    @Override
    public void onDestroy() {
        mView = null;
    }
}
