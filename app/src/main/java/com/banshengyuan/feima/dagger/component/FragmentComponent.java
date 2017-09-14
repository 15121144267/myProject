package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.FragmentModule;
import com.banshengyuan.feima.dagger.module.MainActivityModule;
import com.banshengyuan.feima.view.fragment.CompletedOrderFragment;
import com.banshengyuan.feima.view.fragment.PendingOrderFragment;
import com.banshengyuan.feima.view.fragment.SendingOrderFragment;
import com.banshengyuan.feima.view.fragment.ShoppingCardFragment;

import dagger.Component;

/**
 * Created by helei on 2017/5/3.
 * FragmentComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {MainActivityModule.class, FragmentModule.class})
public interface FragmentComponent extends MainActivityComponent{
    AppCompatActivity activity();
    void inject(PendingOrderFragment pendingOrderFragment);
    void inject(SendingOrderFragment sendingOrderFragment);
    void inject(CompletedOrderFragment completedOrderFragment);
    void inject(ShoppingCardFragment shoppingCardFragment);
}
