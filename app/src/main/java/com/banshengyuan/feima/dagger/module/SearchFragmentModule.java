package com.banshengyuan.feima.dagger.module;


import android.support.v7.app.AppCompatActivity;

import dagger.Module;

/**
 * Created by helei on 2017/5/3.
 * FragmentModule
 */
@Module
public class SearchFragmentModule {
    private final AppCompatActivity activity;

    public SearchFragmentModule(AppCompatActivity activity) {
        this.activity = activity;
    }

}
