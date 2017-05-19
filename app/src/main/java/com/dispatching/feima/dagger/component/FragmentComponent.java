package com.dispatching.feima.dagger.component;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.dagger.module.FragmentModule;
import com.dispatching.feima.dagger.module.MainActivityModule;
import com.dispatching.feima.view.fragment.CompletedOrderFragment;
import com.dispatching.feima.view.fragment.PendingOrderFragment;
import com.dispatching.feima.view.fragment.SendingOrderFragment;

import dagger.Component;

/**
 * Created by helei on 2017/5/3.
 * FragmentComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {MainActivityModule.class, FragmentModule.class})
public interface FragmentComponent extends MainActivityComponent{
    void inject(PendingOrderFragment pendingOrderFragment);
    void inject(SendingOrderFragment sendingOrderFragment);
    void inject(CompletedOrderFragment completedOrderFragment);
}
