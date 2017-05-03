package com.dispatching.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.network.RetrofitUtil;
import com.dispatching.feima.network.networkapi.MainApi;
import com.dispatching.feima.view.PresenterControl.MainControl;
import com.dispatching.feima.view.PresenterImpl.PresenterMainImpl;
import com.dispatching.feima.view.model.MainModel;
import com.dispatching.feima.view.model.ModelTransform;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
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
                .baseUrl("http://115.159.18.100/")
                .isToJson(false)
                .builder()
                .create(MainApi.class);
    }

    @Provides
    @PerActivity
    MainModel provideMainModel(Gson gson, ModelTransform modelTransform) {
        return new MainModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl("http://115.159.18.100/")
                .isToJson(false)
                .builder()
                .create(MainApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    MainControl.PresenterMain providePresenterMain(PresenterMainImpl presenterMain) {
        return presenterMain;
    }
}
