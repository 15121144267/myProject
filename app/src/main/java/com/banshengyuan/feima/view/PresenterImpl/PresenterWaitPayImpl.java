package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.entity.MyOrdersResponse;
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
    public void requestMyOrderList(Integer pageNo, Integer pageSize, String status,boolean flag,String token) {
        Disposable disposable = mModel.myOrderListRequest(pageNo, pageSize, status, flag, token).compose(mView.applySchedulers())
                .subscribe(this::getMyOrderListSuccess,
                        throwable -> mView.loadFail(throwable));
        mView.addSubscription(disposable);
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
