package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.ShopBlockApi;
import com.banshengyuan.feima.view.PresenterControl.ShopBlockControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterShopBlockImpl;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.banshengyuan.feima.view.model.ShopBlockModel;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class ShopBlockActivityModule {
    private final AppCompatActivity activity;
    private final ShopBlockControl.ShopBlockView view;

    public ShopBlockActivityModule(AppCompatActivity activity, ShopBlockControl.ShopBlockView view) {
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
    ShopBlockControl.ShopBlockView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    ShopBlockModel provideShopBlockModel(Gson gson, ModelTransform modelTransform) {
        return new ShopBlockModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(ShopBlockApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    ShopBlockControl.PresenterShopBlock providePresenterShopBlock(PresenterShopBlockImpl presenter) {
        return presenter;
    }
}
