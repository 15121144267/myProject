package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.OrderDetailApi;
import com.banshengyuan.feima.view.PresenterControl.OrderDetailControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterOrderDetailImpl;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.banshengyuan.feima.view.model.OrderDetailModel;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * OrderDetailActivityModule
 */
@Module
public class OrderDetailActivityModule {
    private final AppCompatActivity activity;
    private final OrderDetailControl.OrderDetailView view;

    public OrderDetailActivityModule(AppCompatActivity activity,OrderDetailControl.OrderDetailView view) {
        this.activity = activity;
        this.view = view;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    OrderDetailControl.OrderDetailView view() {
        return this.view;
    }


    @Provides
    @PerActivity
    OrderDetailModel provideOrderDetailModel(Gson gson, ModelTransform modelTransform ) {
        return new OrderDetailModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(OrderDetailApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    OrderDetailControl.PresenterOrderDetail providePresenterOrderDetail(PresenterOrderDetailImpl presenter) {
        return presenter;
    }
}
