package com.dispatching.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.entity.BuProcessor;
import com.dispatching.feima.network.RetrofitUtil;
import com.dispatching.feima.network.networkapi.MyOrderApi;
import com.dispatching.feima.view.PresenterControl.MyOrderControl;
import com.dispatching.feima.view.PresenterImpl.PresenterMyOrderImpl;
import com.dispatching.feima.view.model.ModelTransform;
import com.dispatching.feima.view.model.MyOrderModel;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class MyOrderActivityModule {
    private final AppCompatActivity activity;
    private final MyOrderControl.MyOrderView view;

    public MyOrderActivityModule(AppCompatActivity activity, MyOrderControl.MyOrderView view) {
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
    MyOrderControl.MyOrderView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    MyOrderModel provideMyOrderModel(Gson gson, ModelTransform modelTransform, BuProcessor buProcessor) {
        return new MyOrderModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl("http://118.89.192.122:9997/")
                .isToJson(false)
                .builder()
                .create(MyOrderApi.class), gson, modelTransform,buProcessor);
    }

    @Provides
    @PerActivity
    MyOrderControl.PresenterMyOrder providePresenterMyOrder(PresenterMyOrderImpl presenter) {
        return presenter;
    }
}
