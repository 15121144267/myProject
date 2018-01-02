package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.view.PresenterControl.MyOrderControl;
import com.banshengyuan.feima.view.model.MyOrderModel;

import javax.inject.Inject;

/**
 * Created by helei on 2017/5/3.
 * PresenterCompletedImpl
 */

public class PresenterMyOrderImpl implements MyOrderControl.PresenterMyOrder {
    private MyOrderControl.MyOrderView mView;
    private final Context mContext;
    private MyOrderModel mModel;

    @Inject
    public PresenterMyOrderImpl(Context context, MyOrderModel model, MyOrderControl.MyOrderView view) {
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
