package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.FairProductDetailApi;
import com.banshengyuan.feima.view.PresenterControl.FairProductDetailControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterFairProductDetailImpl;
import com.banshengyuan.feima.view.model.FairProductDetailModel;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class FairProductDetailActivityModule {
    private final AppCompatActivity activity;
    private final FairProductDetailControl.FairProductDetailView view;

    public FairProductDetailActivityModule(AppCompatActivity activity, FairProductDetailControl.FairProductDetailView view) {
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
    FairProductDetailControl.FairProductDetailView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    FairProductDetailModel provideFairProductDetailModel(Gson gson, ModelTransform modelTransform) {
        return new FairProductDetailModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(FairProductDetailApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    FairProductDetailControl.PresenterFairProductDetail providePresenterFairProductDetail(PresenterFairProductDetailImpl presenter) {
        return presenter;
    }
}
