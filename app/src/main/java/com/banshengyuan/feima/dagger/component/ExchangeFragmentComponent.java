package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.ExchangeFragmentModule;
import com.banshengyuan.feima.dagger.module.MainActivityModule;
import com.banshengyuan.feima.view.fragment.CelebrityFragment;
import com.banshengyuan.feima.view.fragment.FollowFragment;
import com.banshengyuan.feima.view.fragment.TrendsFragment;

import dagger.Component;

/**
 * Created by helei on 2017/5/3.
 * FragmentComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {MainActivityModule.class, ExchangeFragmentModule.class})
public interface ExchangeFragmentComponent extends MainActivityComponent{
    AppCompatActivity activity();
    void inject(TrendsFragment trendsFragment);
    void inject(FollowFragment followFragment);
    void inject(CelebrityFragment celebrityFragment);
}
