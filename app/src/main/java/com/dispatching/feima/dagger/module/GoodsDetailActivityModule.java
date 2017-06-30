package com.dispatching.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.network.RetrofitUtil;
import com.dispatching.feima.network.networkapi.GoodsDetailApi;
import com.dispatching.feima.view.PresenterControl.GoodsDetailControl;
import com.dispatching.feima.view.PresenterImpl.PresenterGoodsDetailImpl;
import com.dispatching.feima.view.model.GoodsDetailModel;
import com.dispatching.feima.view.model.ModelTransform;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class GoodsDetailActivityModule {
    private final AppCompatActivity activity;
    private final GoodsDetailControl.GoodsDetailView view;

    public GoodsDetailActivityModule(AppCompatActivity activity, GoodsDetailControl.GoodsDetailView view) {
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
    GoodsDetailControl.GoodsDetailView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    GoodsDetailModel provideGoodsDetailModel(Gson gson, ModelTransform modelTransform ) {
        return new GoodsDetailModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isToJson(false)
                .builder()
                .create(GoodsDetailApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    GoodsDetailControl.PresenterGoodsDetail providePresenterGoodsDetail(PresenterGoodsDetailImpl presenter) {
        return presenter;
    }
}
