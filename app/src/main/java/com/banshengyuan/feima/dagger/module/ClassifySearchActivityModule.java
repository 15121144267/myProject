package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.GoodsClassifyApi;
import com.banshengyuan.feima.view.PresenterControl.ClassifySearchControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterClassifySearchImpl;
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
public class ClassifySearchActivityModule {
    private final AppCompatActivity activity;
    private final ClassifySearchControl.ClassifySearchView view;

    public ClassifySearchActivityModule(AppCompatActivity activity, ClassifySearchControl.ClassifySearchView view) {
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
    ClassifySearchControl.ClassifySearchView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    GoodsClassifyModel provideSearchModel(Gson gson, ModelTransform modelTransform ) {
        return new GoodsClassifyModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.GOODS_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(GoodsClassifyApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    ClassifySearchControl.PresenterClassifySearch providePresenterClassifySearch(PresenterClassifySearchImpl presenter) {
        return presenter;
    }
}
