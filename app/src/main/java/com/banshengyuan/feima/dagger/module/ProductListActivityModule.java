package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.ProductListApi;
import com.banshengyuan.feima.view.PresenterControl.ProductListControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterProductListImpl;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.banshengyuan.feima.view.model.ProductListModel;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class ProductListActivityModule {
    private final AppCompatActivity activity;
    private final ProductListControl.ProductListView view;

    public ProductListActivityModule(AppCompatActivity activity, ProductListControl.ProductListView view) {
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
    ProductListControl.ProductListView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    ProductListModel provideAddAddressModel(Gson gson, ModelTransform modelTransform) {
        return new ProductListModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(ProductListApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    ProductListControl.PresenterProductList providePresenterProductList(PresenterProductListImpl presenter) {
        return presenter;
    }
}
