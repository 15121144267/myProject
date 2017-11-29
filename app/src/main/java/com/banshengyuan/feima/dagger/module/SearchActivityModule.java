package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.SearchApi;
import com.banshengyuan.feima.network.networkapi.SearchOtherApi;
import com.banshengyuan.feima.view.PresenterControl.SearchControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterSearchImpl;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.banshengyuan.feima.view.model.SearchModel;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class SearchActivityModule {
    private final AppCompatActivity activity;
    private final SearchControl.SearchView view;

    public SearchActivityModule(AppCompatActivity activity, SearchControl.SearchView view) {
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
    SearchControl.SearchView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    SearchModel provideSearchModel(Gson gson, ModelTransform modelTransform ) {
        return new SearchModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(SearchApi.class),
                new RetrofitUtil.Builder()
                        .context(activity)
                        .baseUrl(BuildConfig.DISPATCH_SERVICE)
                        .isToJson(false)
                        .builder()
                        .create(SearchOtherApi.class),
                gson, modelTransform);
    }

    @Provides
    @PerActivity
    SearchControl.PresenterSearch providePresenterSearch(PresenterSearchImpl presenter) {
        return presenter;
    }
}
