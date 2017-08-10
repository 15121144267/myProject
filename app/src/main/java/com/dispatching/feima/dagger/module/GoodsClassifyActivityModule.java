package com.dispatching.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.network.RetrofitUtil;
import com.dispatching.feima.network.networkapi.GoodsClassifyApi;
import com.dispatching.feima.view.PresenterControl.GoodsClassifyControl;
import com.dispatching.feima.view.PresenterImpl.PresenterGoodsClassifyImpl;
import com.dispatching.feima.view.model.GoodsClassifyModel;
import com.dispatching.feima.view.model.ModelTransform;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class GoodsClassifyActivityModule {
    private final AppCompatActivity activity;
    private final GoodsClassifyControl.GoodsClassifyView view;

    public GoodsClassifyActivityModule(AppCompatActivity activity, GoodsClassifyControl.GoodsClassifyView view) {
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
    GoodsClassifyControl.GoodsClassifyView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    GoodsClassifyModel provideGoodsClassifyModel(Gson gson, ModelTransform modelTransform) {
        return new GoodsClassifyModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(GoodsClassifyApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    GoodsClassifyControl.PresenterGoodsClassify providePresenterGoodsClassify(PresenterGoodsClassifyImpl presenter) {
        return presenter;
    }
}
