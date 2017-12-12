package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MyOrdersResponse;
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

    private void getOrderDetailInfoSuccess(ResponseData responseData) {
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
