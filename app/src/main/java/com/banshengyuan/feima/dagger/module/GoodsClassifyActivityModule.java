package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.GoodsClassifyApi;
import com.banshengyuan.feima.view.PresenterControl.GoodsClassifyControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterGoodsClassifyImpl;
import com.banshengyuan.feima.view.model.GoodsClassifyModel;
import com.banshengyuan.feima.view.model.ModelTransform;
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
                .baseUrl(BuildConfig.GOODS_SERVICE)
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
