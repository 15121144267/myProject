package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.AddShoppingCardApi;
import com.banshengyuan.feima.view.PresenterControl.ShoppingCardControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterShoppingCardImpl;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.banshengyuan.feima.view.model.ShoppingCardModel;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class ShoppingCardActivityModule {
    private final AppCompatActivity activity;
    private final ShoppingCardControl.ShoppingCardView view;

    public ShoppingCardActivityModule(AppCompatActivity activity, ShoppingCardControl.ShoppingCardView view) {
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
    ShoppingCardControl.ShoppingCardView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    ShoppingCardModel provideShoppingCardModel(Gson gson, ModelTransform modelTransform ) {
        return new ShoppingCardModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.ORDER_SERVICE)
                .isToJson(false)
                .builder()
                .create(AddShoppingCardApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    ShoppingCardControl.PresenterShoppingCard providePresenterShoppingCard(PresenterShoppingCardImpl presenter) {
        return presenter;
    }
}
