package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.ShopListApi;
import com.banshengyuan.feima.network.networkapi.ShopOtherListApi;
import com.banshengyuan.feima.view.PresenterControl.ShopListControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterShopListImpl;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.banshengyuan.feima.view.model.ShopListModel;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class ShopListActivityModule {
    private final AppCompatActivity activity;
    private final ShopListControl.ShopListView view;

    public ShopListActivityModule(AppCompatActivity activity, ShopListControl.ShopListView view) {
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
    ShopListControl.ShopListView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    ShopListModel provideShopListModel(Gson gson, ModelTransform modelTransform) {
        return new ShopListModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl("http://console.freemudvip.com/service/restful/")
                .isToJson(false)
                .builder()
                .create(ShopListApi.class),
                new RetrofitUtil.Builder()
                        .context(activity)
                        .baseUrl("http://member-api-tst.sandload.cn:8735/")
                        .isToJson(false)
                        .builder()
                        .create(ShopOtherListApi.class)
                , gson, modelTransform);
    }

    @Provides
    @PerActivity
    ShopListControl.PresenterShopList providePresenterShopList(PresenterShopListImpl presenter) {
        return presenter;
    }
}
