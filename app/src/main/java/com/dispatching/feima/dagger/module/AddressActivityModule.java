package com.dispatching.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.network.RetrofitUtil;
import com.dispatching.feima.network.networkapi.AddressApi;
import com.dispatching.feima.view.PresenterControl.AddressControl;
import com.dispatching.feima.view.PresenterImpl.PresenterAddressImpl;
import com.dispatching.feima.view.model.AddressModel;
import com.dispatching.feima.view.model.ModelTransform;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class AddressActivityModule {
    private final AppCompatActivity activity;
    private final AddressControl.AddressView view;

    public AddressActivityModule(AppCompatActivity activity, AddressControl.AddressView view) {
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
    AddressControl.AddressView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    AddressModel provideAddressModel(Gson gson, ModelTransform modelTransform ) {
        return new AddressModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isToJson(false)
                .builder()
                .create(AddressApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    AddressControl.PresenterAddress providePresenterAddress(PresenterAddressImpl presenter) {
        return presenter;
    }
}
