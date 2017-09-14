package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.AddShoppingCardApi;
import com.banshengyuan.feima.network.networkapi.GoodsDetailApi;
import com.banshengyuan.feima.view.PresenterControl.GoodsDetailControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterGoodsDetailImpl;
import com.banshengyuan.feima.view.model.GoodsDetailModel;
import com.banshengyuan.feima.view.model.ModelTransform;
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
    GoodsDetailModel provideGoodsDetailModel(Gson gson, ModelTransform modelTransform, BuProcessor buProcessor) {
        return new GoodsDetailModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.GOODS_SERVICE)
                .isToJson(false)
                .builder()
                .create(GoodsDetailApi.class), new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.ORDER_SERVICE)
                .isToJson(false)
                .builder()
                .create(AddShoppingCardApi.class), gson, modelTransform, buProcessor);
    }

    @Provides
    @PerActivity
    GoodsDetailControl.PresenterGoodsDetail providePresenterGoodsDetail(PresenterGoodsDetailImpl presenter) {
        return presenter;
    }
}
