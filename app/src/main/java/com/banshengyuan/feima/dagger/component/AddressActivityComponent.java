package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.AddressActivityModule;
import com.banshengyuan.feima.view.PresenterControl.AddressControl;
import com.banshengyuan.feima.view.activity.AddressActivity;

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
