package com.dispatching.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.dagger.module.AddressActivityModule;
import com.dispatching.feima.view.PresenterControl.AddressControl;
import com.dispatching.feima.view.activity.AddressActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = AddressActivityModule.class)
public interface AddressActivityComponent {
    AppCompatActivity activity();
    AddressControl.AddressView view();
    void inject(AddressActivity addressActivity);
}
