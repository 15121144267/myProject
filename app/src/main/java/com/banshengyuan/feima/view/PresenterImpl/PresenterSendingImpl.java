package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.view.PresenterControl.SendingOrderControl;
import com.banshengyuan.feima.view.model.MainModel;

import javax.inject.Inject;

/**
 * Created by helei on 2017/5/3.
 * PresenterSendingImpl
 */

public class PresenterSendingImpl implements SendingOrderControl.PresenterSendingOrder {
    private final MainModel mMainModel;
    private SendingOrderControl.SendingOrderView mView;
    private final Context mContext;

    @Inject
    public PresenterSendingImpl(Context context, MainModel model,SendingOrderControl.SendingOrderView view) {
        mContext = context;
        mMainModel = model;
        mView = view;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
