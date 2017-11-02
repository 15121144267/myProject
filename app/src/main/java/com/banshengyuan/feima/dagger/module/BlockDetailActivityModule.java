package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.BlockDetailApi;
import com.banshengyuan.feima.view.PresenterControl.BlockDetailControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterBlockDetailImpl;
import com.banshengyuan.feima.view.model.BlockDetailModel;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class BlockDetailActivityModule {
    private final AppCompatActivity activity;
    private final BlockDetailControl.BlockDetailView view;

    public BlockDetailActivityModule(AppCompatActivity activity, BlockDetailControl.BlockDetailView view) {
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
    BlockDetailControl.BlockDetailView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    BlockDetailModel provideBlockDetailModel(Gson gson, ModelTransform modelTransform ) {
        return new BlockDetailModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(BlockDetailApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    BlockDetailControl.PresenterBlockDetail providePresenterBlockDetail(PresenterBlockDetailImpl presenter) {
        return presenter;
    }
}
