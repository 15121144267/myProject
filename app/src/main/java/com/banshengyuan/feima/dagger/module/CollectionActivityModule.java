package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.CollectionApi;
import com.banshengyuan.feima.view.PresenterControl.CollectionBlockControl;
import com.banshengyuan.feima.view.PresenterControl.CollectionControl;
import com.banshengyuan.feima.view.PresenterControl.CollectionFairControl;
import com.banshengyuan.feima.view.PresenterControl.CollectionHotControl;
import com.banshengyuan.feima.view.PresenterControl.CollectionProductControl;
import com.banshengyuan.feima.view.PresenterControl.CollectionShopControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterCollectionBlockImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterCollectionFairImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterCollectionHotImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterCollectionImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterCollectionProductImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterCollectionShopImpl;
import com.banshengyuan.feima.view.model.CollectionModel;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class CollectionActivityModule {
    private final AppCompatActivity activity;
    private CollectionControl.CollectionView view;

    public CollectionActivityModule(AppCompatActivity activity, CollectionControl.CollectionView view) {
        this.activity = activity;
        this.view = view;
    }

    public CollectionActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    CollectionControl.CollectionView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    CollectionModel provideCollectionModel(Gson gson, ModelTransform modelTransform) {
        return new CollectionModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(CollectionApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    CollectionControl.PresenterCollection providePresenterCollection(PresenterCollectionImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CollectionFairControl.PresenterCollectionFair providePresenterCollectionFair(PresenterCollectionFairImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CollectionProductControl.PresenterCollectionProduct providePresenterCollectionProduct(PresenterCollectionProductImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CollectionHotControl.PresenterCollectionHot providePresenterCollectionHot(PresenterCollectionHotImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CollectionShopControl.PresenterCollectionShop providePresenterCollectionShop(PresenterCollectionShopImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CollectionBlockControl.PresenterCollectionBlock providePresenterCollectionBlock(PresenterCollectionBlockImpl presenter) {
        return presenter;
    }

}
