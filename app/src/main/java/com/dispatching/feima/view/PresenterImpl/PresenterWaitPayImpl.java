package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.entity.MyOrdersResponse;
import com.dispatching.feima.view.PresenterControl.WaitPayControl;
import com.dispatching.feima.view.model.MyOrderModel;
import com.dispatching.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
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
    public void requestMyOrderList(Integer pageNo, Integer pageSize) {
        Disposable disposable = mModel.myOrderListRequest(pageNo, pageSize).compose(mView.applySchedulers())
                .subscribe(this::getMyOrderListSuccess,
                        throwable -> mView.loadFail(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getMyOrderListSuccess(ResponseData responseData) {
        if(responseData.resultCode == 100){
            responseData.parseData(MyOrdersResponse.class);
            MyOrdersResponse response = (MyOrdersResponse) responseData.parsedData;
            mView.getMyOrderListSuccess(response);
        }else {
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
