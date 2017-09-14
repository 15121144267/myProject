package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.AddAddressApi;
import com.banshengyuan.feima.view.PresenterControl.AddAddressControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterAddAddressImpl;
import com.banshengyuan.feima.view.model.AddAddressModel;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class AddAddressActivityModule {
    private final AppCompatActivity activity;
    private final AddAddressControl.AddAddressView view;

    public AddAddressActivityModule(AppCompatActivity activity, AddAddressControl.AddAddressView view) {
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
    AddAddressControl.AddAddressView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    AddAddressModel provideAddAddressModel(Gson gson, ModelTransform modelTransform) {
        return new AddAddressModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(AddAddressApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    AddAddressControl.PresenterAddAddress providePresenterAddAddress(PresenterAddAddressImpl presenter) {
        return presenter;
    }
}
