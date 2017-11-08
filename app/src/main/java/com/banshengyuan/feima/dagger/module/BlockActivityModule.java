package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.BlockApi;
import com.banshengyuan.feima.view.PresenterControl.BlockControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterBlockImpl;
import com.banshengyuan.feima.view.model.BlockModel;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityModule
 */
@Module
public class BlockActivityModule {
    private final AppCompatActivity activity;
    private final BlockControl.BlockView view;

    public BlockActivityModule(AppCompatActivity activity, BlockControl.BlockView view) {
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
    BlockControl.BlockView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    BlockModel provideBlockModel(Gson gson, ModelTransform modelTransform) {
        return new BlockModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME, BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(BlockApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    BlockControl.PresenterBlock providePresenterBlock(PresenterBlockImpl presenter) {
        return presenter;
    }
}
