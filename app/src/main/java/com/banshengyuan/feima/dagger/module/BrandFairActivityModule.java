package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.BrandFairApi;
import com.banshengyuan.feima.view.PresenterControl.BrandFairControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterBrandFairImpl;
import com.banshengyuan.feima.view.model.BrandFairModel;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class BrandFairActivityModule {
    private final AppCompatActivity activity;
    private final BrandFairControl.BrandFairView view;

    public BrandFairActivityModule(AppCompatActivity activity, BrandFairControl.BrandFairView view) {
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
    BrandFairControl.BrandFairView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    BrandFairModel provideBrandFairModel(Gson gson, ModelTransform modelTransform) {
        return new BrandFairModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(BrandFairApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    BrandFairControl.PresenterBrandFair providePresenterBrandFair(PresenterBrandFairImpl presenter) {
        return presenter;
    }
}
