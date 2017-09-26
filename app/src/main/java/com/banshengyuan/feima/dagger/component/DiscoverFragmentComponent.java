package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.DiscoverFragmentModule;
import com.banshengyuan.feima.dagger.module.MainActivityModule;
import com.banshengyuan.feima.view.fragment.FairFragment;
import com.banshengyuan.feima.view.fragment.ProductFragment;
import com.banshengyuan.feima.view.fragment.SellerFragment;

import dagger.Component;

/**
 * Created by helei on 2017/5/3.
 * FragmentComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {MainActivityModule.class, DiscoverFragmentModule.class})
public interface DiscoverFragmentComponent extends MainActivityComponent{
    AppCompatActivity activity();
    void inject(FairFragment fairFragment);
    void inject(ProductFragment productFragment);
    void inject(SellerFragment sellerFragment);
}
