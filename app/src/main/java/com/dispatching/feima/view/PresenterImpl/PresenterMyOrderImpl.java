package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.view.PresenterControl.MyOrderControl;
import com.dispatching.feima.view.model.MainModel;

import javax.inject.Inject;

/**
 * Created by helei on 2017/5/3.
 * PresenterCompletedImpl
 */

public class PresenterMyOrderImpl implements MyOrderControl.PresenterMyOrder {
    private MyOrderControl.MyOrderView mView;
    private final Context mContext;

    @Inject
    public PresenterMyOrderImpl(Context context, MainModel model, MyOrderControl.MyOrderView view) {
        mContext = context;
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
