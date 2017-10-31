package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.CollectionActivityModule;
import com.banshengyuan.feima.dagger.module.CollectionFragmentModule;
import com.banshengyuan.feima.view.fragment.CollectionBlockFragment;
import com.banshengyuan.feima.view.fragment.CollectionFairFragment;
import com.banshengyuan.feima.view.fragment.CollectionHotFragment;
import com.banshengyuan.feima.view.fragment.CollectionProductFragment;
import com.banshengyuan.feima.view.fragment.CollectionShopFragment;

import dagger.Component;

/**
 * Created by helei on 2017/5/3.
 * FragmentComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {CollectionActivityModule.class, CollectionFragmentModule.class})
public interface CollectionFragmentComponent extends CollectionActivityComponent {
    AppCompatActivity activity();

    void inject(CollectionFairFragment collectionFairFragment);

    void inject(CollectionProductFragment collectionProductFragment);

    void inject(CollectionHotFragment collectionHotFragment);

    void inject(CollectionShopFragment collectionShopFragment);

    void inject(CollectionBlockFragment collectionBlockFragment);

}
