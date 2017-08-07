package com.dispatching.feima.dagger.module;


import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.view.PresenterControl.AllOrderControl;
import com.dispatching.feima.view.PresenterControl.LoadDataView;
import com.dispatching.feima.view.PresenterControl.OrderCompleteControl;
import com.dispatching.feima.view.PresenterControl.PayCompleteControl;
import com.dispatching.feima.view.PresenterControl.WaitPayControl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/5/3.
 * FragmentModule
 */
@Module
public class OrderFragmentModule {
    private final AppCompatActivity activity;

    private WaitPayControl.WaitPayView mWaitPayView;
    private PayCompleteControl.PayCompleteView mPayCompleteView;
    private OrderCompleteControl.OrderCompleteView mOrderCompleteView;
    private AllOrderControl.AllOrderView mAllOrderView;

    public OrderFragmentModule(LoadDataView view, AppCompatActivity activity) {
        this.activity = activity;
        if (view instanceof WaitPayControl.WaitPayView) {
            mWaitPayView = (WaitPayControl.WaitPayView) view;
        } else if (view instanceof PayCompleteControl.PayCompleteView) {
            mPayCompleteView = (PayCompleteControl.PayCompleteView) view;
        } else if (view instanceof OrderCompleteControl.OrderCompleteView) {
            mOrderCompleteView = (OrderCompleteControl.OrderCompleteView) view;
        } else if (view instanceof AllOrderControl.AllOrderView) {
            mAllOrderView = (AllOrderControl.AllOrderView) view;
        }
    }

    @Provides
    @PerActivity
    WaitPayControl.WaitPayView waitPayView() {
        return mWaitPayView;
    }

    @Provides
    @PerActivity
    PayCompleteControl.PayCompleteView payCompleteView() {
        return mPayCompleteView;
    }

    @Provides
    @PerActivity
    OrderCompleteControl.OrderCompleteView orderCompleteView() {
        return mOrderCompleteView;
    }

    @Provides
    @PerActivity
    AllOrderControl.AllOrderView orderAllOrderView() {
        return mAllOrderView;
    }

}
