package com.dispatching.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * BaseActivityModule
 */
@Module
public class BaseActivityModule {
    private final AppCompatActivity activity;

    public BaseActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }
}
