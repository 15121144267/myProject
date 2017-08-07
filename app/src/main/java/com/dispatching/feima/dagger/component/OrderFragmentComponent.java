package com.dispatching.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.dagger.module.MyOrderActivityModule;
import com.dispatching.feima.dagger.module.OrderFragmentModule;
import com.dispatching.feima.view.fragment.AllOrderFragment;
import com.dispatching.feima.view.fragment.OrderCompleteFragment;
import com.dispatching.feima.view.fragment.PayCompleteOrderFragment;
import com.dispatching.feima.view.fragment.WaitPayOrderFragment;

import dagger.Component;

/**
 * Created by helei on 2017/5/3.
 * FragmentComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {MyOrderActivityModule.class, OrderFragmentModule.class})
public interface OrderFragmentComponent extends MyOrderActivityComponent{
    AppCompatActivity activity();
    void inject(WaitPayOrderFragment fragment);
    void inject(PayCompleteOrderFragment fragment);
    void inject(OrderCompleteFragment fragment);
    void inject(AllOrderFragment fragment);
}
