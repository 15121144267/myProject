package com.dispatching.feima.dagger.module;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.view.PresenterControl.CompletedOrderControl;
import com.dispatching.feima.view.PresenterControl.LoadDataView;
import com.dispatching.feima.view.PresenterControl.PendingOrderControl;
import com.dispatching.feima.view.PresenterControl.SendingOrderControl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/5/3.
 * FragmentModule
 */
@Module
public class FragmentModule {
    private PendingOrderControl.PendingOrderView pendingView;
    private SendingOrderControl.SendingOrderView sendingView;
    private CompletedOrderControl.CompletedOrderView completeView;

    public FragmentModule(LoadDataView view) {
        if (view instanceof PendingOrderControl.PendingOrderView) {
            pendingView = (PendingOrderControl.PendingOrderView) view;
        } else if (view instanceof SendingOrderControl.SendingOrderView) {
            sendingView = (SendingOrderControl.SendingOrderView) view;
        } else {
            completeView = (CompletedOrderControl.CompletedOrderView) view;
        }
    }

    @Provides
    @PerActivity
    PendingOrderControl.PendingOrderView  pendingView() {
        return this.pendingView;
    }

    @Provides
    @PerActivity
    SendingOrderControl.SendingOrderView sendingView() {
        return this.sendingView;
    }

    @Provides
    @PerActivity
    CompletedOrderControl.CompletedOrderView completeView() {
        return this.completeView;
    }
}
