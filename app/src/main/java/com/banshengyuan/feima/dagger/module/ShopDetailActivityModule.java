package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.ShopDetailApi;
import com.banshengyuan.feima.network.networkapi.ShopDetailOtherApi;
import com.banshengyuan.feima.view.PresenterControl.ShopDetailControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterShopDetailImpl;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.banshengyuan.feima.view.model.ShopDetailModel;
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
    ShopDetailModel provideShopDetailModel(Gson gson, ModelTransform modelTransform) {
        return new ShopDetailModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.GOODS_SERVICE)
                .isToJson(false)
                .builder()
                .create(ShopDetailApi.class),
                new RetrofitUtil.Builder()
                        .context(activity)
                        .baseUrl("http://member-api-tst.sandload.cn:8735/")
                        .isToJson(false)
                        .builder()
                        .create(ShopDetailOtherApi.class),
                gson, modelTransform);
    }

    @Provides
    @PerActivity
    ShopDetailControl.PresenterShopDetail providePresenterShopDetail(PresenterShopDetailImpl presenter) {
        return presenter;
    }
}
