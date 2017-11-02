package com.banshengyuan.feima.dagger.module;


import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.ShopListApi;
import com.banshengyuan.feima.network.networkapi.ShopOtherListApi;
import com.banshengyuan.feima.view.PresenterControl.CompletedOrderControl;
import com.banshengyuan.feima.view.PresenterControl.ExChangeControl;
import com.banshengyuan.feima.view.PresenterControl.LoadDataView;
import com.banshengyuan.feima.view.PresenterControl.PendingOrderControl;
import com.banshengyuan.feima.view.PresenterControl.SendingOrderControl;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.banshengyuan.feima.view.model.ShopListModel;
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
    private ExChangeControl.ExChangeView exChangeView;

    public FragmentModule(LoadDataView view, AppCompatActivity activity) {
        this.activity = activity;
        if (view instanceof PendingOrderControl.PendingOrderView) {
            pendingView = (PendingOrderControl.PendingOrderView) view;
        } else if (view instanceof SendingOrderControl.SendingOrderView) {
            sendingView = (SendingOrderControl.SendingOrderView) view;
        } else if (view instanceof ExChangeControl.ExChangeView) {
            exChangeView = (ExChangeControl.ExChangeView) view;
        } else {
            completeView = (CompletedOrderControl.CompletedOrderView) view;
        }
    }

    @Provides
    @PerActivity
    ShopListModel provideShopListModel(Gson gson, ModelTransform modelTransform) {
        return new ShopListModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl("http://console.freemudvip.com/service/restful/")
                .isToJson(false)
                .builder()
                .create(ShopListApi.class),
                new RetrofitUtil.Builder()
                        .context(activity)
                        .baseUrl(BuildConfig.DISPATCH_SERVICE)
                        .isToJson(false)
                        .builder()
                        .create(ShopOtherListApi.class),
                gson, modelTransform);
    }

    @Provides
    @PerActivity
    PendingOrderControl.PendingOrderView pendingView() {
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

    @Provides
    @PerActivity
    ExChangeControl.ExChangeView shoppingCardView() {
        return this.exChangeView;
    }
}
