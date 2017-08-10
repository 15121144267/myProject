package com.dispatching.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.network.RetrofitUtil;
import com.dispatching.feima.network.networkapi.AddShoppingCardApi;
import com.dispatching.feima.view.PresenterControl.ShoppingCardControl;
import com.dispatching.feima.view.PresenterImpl.PresenterShoppingCardImpl;
import com.dispatching.feima.view.model.ModelTransform;
import com.dispatching.feima.view.model.ShoppingCardModel;
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
