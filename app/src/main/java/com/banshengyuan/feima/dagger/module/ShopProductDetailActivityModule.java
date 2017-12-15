package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.ShopProductDetailApi;
import com.banshengyuan.feima.view.PresenterControl.ShopProductDetailControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterShopProductDetailImpl;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.banshengyuan.feima.view.model.ShopProductDetailModel;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class ShopProductDetailActivityModule {
    private final AppCompatActivity activity;
    private final ShopProductDetailControl.ShopProductDetailView view;

    public ShopProductDetailActivityModule(AppCompatActivity activity, ShopProductDetailControl.ShopProductDetailView view) {
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
    ShopProductDetailControl.ShopProductDetailView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    ShopProductDetailModel provideAddAddressModel(Gson gson, ModelTransform modelTransform, BuProcessor buProcessor) {
        return new ShopProductDetailModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(ShopProductDetailApi.class), gson, modelTransform,buProcessor);
    }

    @Provides
    @PerActivity
    ShopProductDetailControl.PresenterShopProductDetail providePresenterShopProductDetail(PresenterShopProductDetailImpl presenter) {
        return presenter;
    }
}
