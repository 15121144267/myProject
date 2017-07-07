package com.dispatching.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.network.RetrofitUtil;
import com.dispatching.feima.network.networkapi.ShopDetailApi;
import com.dispatching.feima.view.PresenterControl.ShopDetailControl;
import com.dispatching.feima.view.PresenterImpl.PresenterShopDetailImpl;
import com.dispatching.feima.view.model.ModelTransform;
import com.dispatching.feima.view.model.ShopDetailModel;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class ShopDetailActivityModule {
    private final AppCompatActivity activity;
    private final ShopDetailControl.ShopDetailView view;

    public ShopDetailActivityModule(AppCompatActivity activity, ShopDetailControl.ShopDetailView view) {
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
    ShopDetailControl.ShopDetailView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    ShopDetailModel provideShopDetailModel(Gson gson, ModelTransform modelTransform ) {
        return new ShopDetailModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl("http://118.89.192.122:9998/")
                .isToJson(false)
                .builder()
                .create(ShopDetailApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    ShopDetailControl.PresenterShopDetail providePresenterShopDetail(PresenterShopDetailImpl presenter) {
        return presenter;
    }
}
