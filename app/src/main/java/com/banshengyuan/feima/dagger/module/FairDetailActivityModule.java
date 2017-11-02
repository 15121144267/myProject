package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.FairDetailApi;
import com.banshengyuan.feima.view.PresenterControl.CelebrityControl;
import com.banshengyuan.feima.view.PresenterControl.FairDetailControl;
import com.banshengyuan.feima.view.PresenterControl.FollowControl;
import com.banshengyuan.feima.view.PresenterControl.TrendsControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterCelebrityImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterFairDetailImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterFollowImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterTrendsImpl;
import com.banshengyuan.feima.view.model.FairDetailModel;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class FairDetailActivityModule {
    private final AppCompatActivity activity;
    private FairDetailControl.FairDetailView view;

    public FairDetailActivityModule(AppCompatActivity activity, FairDetailControl.FairDetailView view) {
        this.activity = activity;
        this.view = view;
    }

    public FairDetailActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    FairDetailControl.FairDetailView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    FairDetailModel provideAddAddressModel(Gson gson, ModelTransform modelTransform) {
        return new FairDetailModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME, BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(FairDetailApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    FairDetailControl.PresenterFairDetail providePresenterAddAddress(PresenterFairDetailImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    TrendsControl.PresenterTrends providePresenterTrends(PresenterTrendsImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    FollowControl.PresenterFollow providePresenterFollow(PresenterFollowImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CelebrityControl.PresenterCelebrity providePresenterCelebrity(PresenterCelebrityImpl presenter) {
        return presenter;
    }
}
