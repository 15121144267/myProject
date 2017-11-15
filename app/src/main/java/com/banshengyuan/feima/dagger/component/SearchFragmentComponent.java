package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.SearchActivityModule;
import com.banshengyuan.feima.dagger.module.SearchFragmentModule;
import com.banshengyuan.feima.view.fragment.SearchBlockFragment;
import com.banshengyuan.feima.view.fragment.SearchFairFragment;
import com.banshengyuan.feima.view.fragment.SearchProductFragment;
import com.banshengyuan.feima.view.fragment.SearchShopFragment;

import dagger.Component;

/**
 * Created by helei on 2017/5/3.
 * FragmentComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {SearchActivityModule.class, SearchFragmentModule.class})
public interface SearchFragmentComponent extends SearchActivityComponent{
    AppCompatActivity activity();
    void inject(SearchFairFragment searchFairFragment);
    void inject(SearchProductFragment searchProductFragment);
    void inject(SearchShopFragment searchShopFragment);
    void inject(SearchBlockFragment searchBlockFragment);
}
