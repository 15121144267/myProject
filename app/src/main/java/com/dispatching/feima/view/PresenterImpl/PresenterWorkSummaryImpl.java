package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.OrderDeliveryResponse;
import com.dispatching.feima.view.PresenterControl.WorkSummaryControl;
import com.dispatching.feima.view.model.ResponseData;
import com.dispatching.feima.view.model.WorkSummaryModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/5/17.
 * PresenterWorkSummaryImpl
 */

public class PresenterWorkSummaryImpl implements WorkSummaryControl.PresenterWorkSummary {
    private WorkSummaryControl.WorkSummaryView mView;
    private final Context mContext;
    private final WorkSummaryModel mWorkSummaryModel;
    @Inject
    public PresenterWorkSummaryImpl(Context context , WorkSummaryModel workSummaryModel,WorkSummaryControl.WorkSummaryView view) {
        mContext = context;
        mWorkSummaryModel = workSummaryModel;
        mView = view;
    }

    @Override
    public void requestAllOrderInfo(String token, String uId, String startTime, String endTime) {

        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable =  mWorkSummaryModel.AllOrderInfoRequest(token,uId,startTime,endTime).compose(mView.applySchedulers())
                .subscribe(this::getAllOrderSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getAllOrderSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            responseData.parseData(OrderDeliveryResponse.class);
            OrderDeliveryResponse response = (OrderDeliveryResponse) responseData.parsedData;
            mView.getAllOrderSuccess(response);
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
