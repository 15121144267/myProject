package com.dispatching.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.network.RetrofitUtil;
import com.dispatching.feima.network.networkapi.MainApi;
import com.dispatching.feima.view.PresenterControl.MainControl;
import com.dispatching.feima.view.PresenterControl.PendingOrderControl;
import com.dispatching.feima.view.PresenterControl.SendingOrderControl;
import com.dispatching.feima.view.PresenterControl.CompletedOrderControl;
import com.dispatching.feima.view.PresenterImpl.PresenterCompletedImpl;
import com.dispatching.feima.view.PresenterImpl.PresenterMainImpl;
import com.dispatching.feima.view.PresenterImpl.PresenterPendingImpl;
import com.dispatching.feima.view.PresenterImpl.PresenterSendingImpl;
import com.dispatching.feima.view.model.MainModel;
import com.dispatching.feima.view.model.ModelTransform;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * MainActivityModule
 */
@Module
public class MainActivityModule {
    private final AppCompatActivity activity;

    public MainActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    MainApi provideMainApi() {
        return new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isToJson(false)
                .builder()
                .create(MainApi.class);
    }

    @Provides
    @PerActivity
    MainModel provideMainModel(Gson gson, ModelTransform modelTransform,MainApi mainApi) {
        return new MainModel(mainApi, gson, modelTransform);
    }

    @Provides
    @PerActivity
    MainControl.PresenterMain providePresenterMain(PresenterMainImpl presenterMain) {
        return presenterMain;
    }

    @Provides
    @PerActivity
    CompletedOrderControl.PresenterCompletedOrder providePresenterCompleted(PresenterCompletedImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    PendingOrderControl.PresenterPendingOrder providePresenterPending(PresenterPendingImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SendingOrderControl.PresenterSendingOrder providePresenterSending(PresenterSendingImpl presenter) {
        return presenter;
    }
}
