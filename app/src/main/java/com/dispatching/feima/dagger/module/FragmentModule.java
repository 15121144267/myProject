package com.dispatching.feima.dagger.module;


import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.network.RetrofitUtil;
import com.dispatching.feima.network.networkapi.ShopListApi;
import com.dispatching.feima.view.PresenterControl.CompletedOrderControl;
import com.dispatching.feima.view.PresenterControl.LoadDataView;
import com.dispatching.feima.view.PresenterControl.PendingOrderControl;
import com.dispatching.feima.view.PresenterControl.SendingOrderControl;
import com.dispatching.feima.view.model.ModelTransform;
import com.dispatching.feima.view.model.ShopListModel;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/5/3.
 * FragmentModule
 */
@Module
public class FragmentModule {
    private final AppCompatActivity activity;

    private PendingOrderControl.PendingOrderView pendingView;
    private SendingOrderControl.SendingOrderView sendingView;
    private CompletedOrderControl.CompletedOrderView completeView;

    public FragmentModule(LoadDataView view, AppCompatActivity activity) {
        this.activity = activity;
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
    PendingOrderControl.PendingOrderView pendingView() {
        return this.pendingView;
    }

    @Provides
    @PerActivity
    ShopListModel provideShopListModel(Gson gson, ModelTransform modelTransform) {
        return new ShopListModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl("http://console.freemudvip.com/service/restful/")
                .isToJson(false)
                .builder()
                .create(ShopListApi.class), gson, modelTransform);
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
