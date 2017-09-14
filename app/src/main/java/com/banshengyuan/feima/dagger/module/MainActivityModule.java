package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.gen.DaoSession;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.AddShoppingCardApi;
import com.banshengyuan.feima.network.networkapi.MainApi;
import com.banshengyuan.feima.view.PresenterControl.CompletedOrderControl;
import com.banshengyuan.feima.view.PresenterControl.MainControl;
import com.banshengyuan.feima.view.PresenterControl.PendingOrderControl;
import com.banshengyuan.feima.view.PresenterControl.SendingOrderControl;
import com.banshengyuan.feima.view.PresenterControl.ShoppingCardControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterCompletedImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterMainImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterPendingImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterSendingImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterShoppingCardImpl;
import com.banshengyuan.feima.view.model.MainModel;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.banshengyuan.feima.view.model.ShoppingCardModel;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * MainActivityModule
 */
@Module
public class MainActivityModule {
    private final AppCompatActivity activity;
    private MainControl.MainView view;

    public MainActivityModule(AppCompatActivity activity, MainControl.MainView view) {
        this.activity = activity;
        this.view = view;
    }

    public MainActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    MainControl.MainView view() {
        return this.view;
    }


    @Provides
    @PerActivity
    MainModel provideMainModel(Gson gson, ModelTransform modelTransform, DaoSession daoSession) {
        return new MainModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME, BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(MainApi.class), gson, modelTransform, daoSession.getOrderNoticeDao());
    }

    @Provides
    @PerActivity
    ShoppingCardModel provideShoppingCardModel(Gson gson, ModelTransform modelTransform) {
        return new ShoppingCardModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.ORDER_SERVICE)
                .isToJson(false)
                .builder()
                .create(AddShoppingCardApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    MainControl.PresenterMain providePresenterMain(PresenterMainImpl presenterMain) {
        return presenterMain;
    }

    @Provides
    @PerActivity
    CompletedOrderControl.PresenterCompletedOrder providePresenterCompleted(PresenterCompletedImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    PendingOrderControl.PresenterPendingOrder providePresenterPending(PresenterPendingImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SendingOrderControl.PresenterSendingOrder providePresenterSending(PresenterSendingImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ShoppingCardControl.PresenterShoppingCard providePresenterShoppingCard(PresenterShoppingCardImpl presenter) {
        return presenter;
    }
}
