package com.dispatching.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.entity.BuProcessor;
import com.dispatching.feima.network.RetrofitUtil;
import com.dispatching.feima.network.networkapi.MyOrderApi;
import com.dispatching.feima.view.PresenterControl.AllOrderControl;
import com.dispatching.feima.view.PresenterControl.MyOrderControl;
import com.dispatching.feima.view.PresenterControl.OrderCompleteControl;
import com.dispatching.feima.view.PresenterControl.PayCompleteControl;
import com.dispatching.feima.view.PresenterControl.WaitPayControl;
import com.dispatching.feima.view.PresenterImpl.PresenterAllOrderImpl;
import com.dispatching.feima.view.PresenterImpl.PresenterMyOrderImpl;
import com.dispatching.feima.view.PresenterImpl.PresenterOrderCompleteImpl;
import com.dispatching.feima.view.PresenterImpl.PresenterPayCompleteImpl;
import com.dispatching.feima.view.PresenterImpl.PresenterWaitPayImpl;
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
                .baseUrl(BuildConfig.ORDER_SERVICE)
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
