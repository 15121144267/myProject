package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.AddAddressActivityModule;
import com.banshengyuan.feima.view.PresenterControl.AddAddressControl;
import com.banshengyuan.feima.view.activity.AddAddressActivity;

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
