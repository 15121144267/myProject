package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.WorkSummaryApi;
import com.banshengyuan.feima.view.PresenterControl.WorkSummaryControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterWorkSummaryImpl;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.banshengyuan.feima.view.model.WorkSummaryModel;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * WorkSummaryActivityModule
 */
@Module
public class WorkSummaryActivityModule {
    private final AppCompatActivity activity;
    private final WorkSummaryControl.WorkSummaryView view;

    public WorkSummaryActivityModule(AppCompatActivity activity,WorkSummaryControl.WorkSummaryView view) {
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
    WorkSummaryControl.WorkSummaryView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    WorkSummaryModel provideWorkSummaryModel(Gson gson, ModelTransform modelTransform) {
        return new WorkSummaryModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isToJson(false)
                .builder()
                .create(WorkSummaryApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    WorkSummaryControl.PresenterWorkSummary providePresenterWorkSummary(PresenterWorkSummaryImpl presenterWorkSummary) {
        return presenterWorkSummary;
    }
}
