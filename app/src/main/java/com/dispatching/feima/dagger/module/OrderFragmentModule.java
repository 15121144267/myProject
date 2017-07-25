package com.dispatching.feima.dagger.module;


import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
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

    public OrderFragmentModule(LoadDataView view, AppCompatActivity activity) {
        this.activity = activity;
        if (view instanceof WaitPayControl.WaitPayView) {
            mWaitPayView = (WaitPayControl.WaitPayView) view;
        } else if (view instanceof PayCompleteControl.PayCompleteView) {
            mPayCompleteView = (PayCompleteControl.PayCompleteView) view;
        } else if (view instanceof OrderCompleteControl.OrderCompleteView) {
            mOrderCompleteView = (OrderCompleteControl.OrderCompleteView) view;
        }
    }

    @Provides
    @PerActivity
    WaitPayControl.WaitPayView waitPayView() {
        return this.mWaitPayView;
    }

    @Provides
    @PerActivity
    PayCompleteControl.PayCompleteView payCompleteView() {
        return this.mPayCompleteView;
    }

    @Provides
    @PerActivity
    OrderCompleteControl.OrderCompleteView orderCompleteView() {
        return this.mOrderCompleteView;
    }

    /*@Provides
    @PerActivity
    ShopListModel provideShopListModel(Gson gson, ModelTransform modelTransform) {
        return new ShopListModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl("http://console.freemudvip.com/service/restful/")
                .isToJson(false)
                .builder()
                .create(ShopListApi.class), gson, modelTransform);
    }*/

    /*@Provides
    @PerActivity
    SendingOrderControl.SendingOrderView sendingView() {
        return this.sendingView;
    }

    @Provides
    @PerActivity
    CompletedOrderControl.CompletedOrderView completeView() {
        return this.completeView;
    }*/
}
