package com.dispatching.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.entity.BuProcessor;
import com.dispatching.feima.network.RetrofitUtil;
import com.dispatching.feima.network.networkapi.PayApi;
import com.dispatching.feima.view.PresenterControl.PayControl;
import com.dispatching.feima.view.PresenterImpl.PresenterPayImpl;
import com.dispatching.feima.view.model.ModelTransform;
import com.dispatching.feima.view.model.PayModel;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class PayActivityModule {
    private final AppCompatActivity activity;
    private final PayControl.PayView view;

    public PayActivityModule(AppCompatActivity activity, PayControl.PayView view) {
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
    PayControl.PayView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    PayModel providePayModel(Gson gson, ModelTransform modelTransform, BuProcessor buProcessor) {
        return new PayModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.ORDER_SERVICE)
                .isToJson(false)
                .builder()
                .create(PayApi.class), gson, modelTransform,buProcessor,activity);
    }

    @Provides
    @PerActivity
    PayControl.PresenterPay providePresenterPay(PresenterPayImpl presenter) {
        return presenter;
    }
}
