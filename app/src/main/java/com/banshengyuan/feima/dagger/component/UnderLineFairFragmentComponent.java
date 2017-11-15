package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.UnderLineFairActivityModule;
import com.banshengyuan.feima.dagger.module.UnderLineFairFragmentModule;
import com.banshengyuan.feima.view.fragment.UnderLineFairFragment;
import com.banshengyuan.feima.view.fragment.UnderLineProductListFragment;
import com.banshengyuan.feima.view.fragment.UnderLineShopFragment;

import dagger.Component;

/**
 * Created by helei on 2017/5/3.
 * FragmentComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {UnderLineFairActivityModule.class, UnderLineFairFragmentModule.class})
public interface UnderLineFairFragmentComponent extends UnderLineFairActivityComponent{
    AppCompatActivity activity();
    void inject(UnderLineShopFragment underLineShopFragment);
    void inject(UnderLineFairFragment underLineFairFragment);
    void inject(UnderLineProductListFragment underLineProductListFragment);
}
