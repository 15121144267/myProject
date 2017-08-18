package com.dispatching.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.network.RetrofitUtil;
import com.dispatching.feima.network.networkapi.SearchApi;
import com.dispatching.feima.view.PresenterControl.SearchControl;
import com.dispatching.feima.view.PresenterImpl.PresenterSearchImpl;
import com.dispatching.feima.view.model.ModelTransform;
import com.dispatching.feima.view.model.SearchModel;
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
                .baseUrl(BuildConfig.GOODS_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(SearchApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    SearchControl.PresenterSearch providePresenterSearch(PresenterSearchImpl presenter) {
        return presenter;
    }
}
