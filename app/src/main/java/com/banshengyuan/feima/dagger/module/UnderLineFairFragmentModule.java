package com.banshengyuan.feima.dagger.module;


import android.support.v7.app.AppCompatActivity;

import dagger.Module;

/**
 * Created by helei on 2017/5/3.
 * FragmentModule
 */
@Module
public class UnderLineFairFragmentModule {
    private final AppCompatActivity activity;

    public UnderLineFairFragmentModule(AppCompatActivity activity) {
        this.activity = activity;
    }

}
