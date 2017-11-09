package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.ExchangeFragmentModule;
import com.banshengyuan.feima.dagger.module.FairDetailActivityModule;
import com.banshengyuan.feima.view.fragment.CelebrityFragment;
import com.banshengyuan.feima.view.fragment.CommentFragment;
import com.banshengyuan.feima.view.fragment.FollowFragment;
import com.banshengyuan.feima.view.fragment.TrendsFragment;

import dagger.Component;

/**
 * Created by helei on 2017/5/3.
 * FragmentComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {FairDetailActivityModule.class, ExchangeFragmentModule.class})
public interface ExchangeFragmentComponent extends FairDetailActivityComponent{
    AppCompatActivity activity();
    void inject(TrendsFragment trendsFragment);
    void inject(FollowFragment followFragment);
    void inject(CelebrityFragment celebrityFragment);
    void inject(CommentFragment commentFragment);
}
