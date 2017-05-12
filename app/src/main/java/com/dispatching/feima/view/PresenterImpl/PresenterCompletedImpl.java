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
 */

public class PresenterCompletedImpl implements CompletedOrderControl.PresenterCompletedOrder {
    private MainModel mMainModel;
    private CompletedOrderControl.CompletedOrderView mView;
    private Context mContext;

    @Inject
    public PresenterCompletedImpl(Context context, MainModel model) {
        mContext = context;
        mMainModel = model;
    }

    @Override
    public void requestCompletedOrder(Integer position, String token, String version, String uId) {
        mView.showLoading(mContext.getString(R.string.loading));
        mMainModel.CompleteOrderInfoRequest(token, version, uId).compose(mView.applySchedulers())
                .subscribe(responseData -> getCompleteOrderSuccess(responseData)
                        , throwable -> mView.getOrderError(throwable), () -> mView.getOrderComplete());
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
    public void setView(CompletedOrderControl.CompletedOrderView completedOrderView) {
        mView = completedOrderView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
