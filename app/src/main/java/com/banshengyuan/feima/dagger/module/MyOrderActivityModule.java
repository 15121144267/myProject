package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.MyOrderApi;
import com.banshengyuan.feima.view.PresenterControl.AllOrderControl;
import com.banshengyuan.feima.view.PresenterControl.MyOrderControl;
import com.banshengyuan.feima.view.PresenterControl.OrderCompleteControl;
import com.banshengyuan.feima.view.PresenterControl.PayCompleteControl;
import com.banshengyuan.feima.view.PresenterControl.WaitPayControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterAllOrderImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterMyOrderImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterOrderCompleteImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterPayCompleteImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterWaitPayImpl;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.banshengyuan.feima.view.model.MyOrderModel;
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
    private  MyOrderControl.MyOrderView view;

    public MyOrderActivityModule(AppCompatActivity activity, MyOrderControl.MyOrderView view) {
        this.activity = activity;
        this.view = view;
    }

    public MyOrderActivityModule(AppCompatActivity activity) {
        this.activity = activity;
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
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isToJson(false)
                .builder()
                .create(MyOrderApi.class), gson, modelTransform, buProcessor);
    }

    @Provides
    @PerActivity
    MyOrderControl.PresenterMyOrder providePresenterMyOrder(PresenterMyOrderImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    WaitPayControl.PresenterWaitPay providePresenterWaitPay(PresenterWaitPayImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    PayCompleteControl.PresenterPayComplete providePresenterPayComplete(PresenterPayCompleteImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    OrderCompleteControl.PresenterOrderComplete providePresenterOrderComplete(PresenterOrderCompleteImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    AllOrderControl.PresenterAllOrderView providePresenterAllOrder(PresenterAllOrderImpl presenter) {
        return presenter;
    }
}
