package com.dispatching.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.dagger.module.AddAddressActivityModule;
import com.dispatching.feima.view.PresenterControl.AddAddressControl;
import com.dispatching.feima.view.activity.AddAddressActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = AddAddressActivityModule.class)
public interface AddAddressActivityComponent {
    AppCompatActivity activity();
    AddAddressControl.AddAddressView view();
    void inject(AddAddressActivity activity);
}
