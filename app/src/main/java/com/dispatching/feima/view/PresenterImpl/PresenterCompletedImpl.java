package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.OrderDeliveryResponse;
import com.dispatching.feima.view.PresenterControl.CompletedOrderControl;
import com.dispatching.feima.view.model.MainModel;
import com.dispatching.feima.view.model.ResponseData;

import javax.inject.Inject;

/**
 * Created by helei on 2017/5/3.
 * PresenterCompletedImpl
 */

public class PresenterCompletedImpl implements CompletedOrderControl.PresenterCompletedOrder {
    private final MainModel mMainModel;
    private CompletedOrderControl.CompletedOrderView mView;
    private final Context mContext;

    @Inject
    public PresenterCompletedImpl(Context context, MainModel model,CompletedOrderControl.CompletedOrderView view) {
        mContext = context;
        mMainModel = model;
        mView = view;
    }

    @Override
    public void requestCompletedOrder(String token, String uId) {
        mView.showLoading(mContext.getString(R.string.loading));
        /*mMainModel.CompleteOrderInfoRequest(token, uId).compose(mView.applySchedulers())
                .subscribe(this::getCompleteOrderSuccess
                        , throwable -> mView.getOrderError(throwable), () -> mView.getOrderComplete());*/
    }

    private void getCompleteOrderSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            responseData.parseData(OrderDeliveryResponse.class);
            OrderDeliveryResponse response = (OrderDeliveryResponse) responseData.parsedData;
            mView.getCompletedOrderSuccess(response);
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
