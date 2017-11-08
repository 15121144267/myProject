package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.BlockActivityModule;
import com.banshengyuan.feima.view.PresenterControl.BlockControl;
import com.banshengyuan.feima.view.activity.BlockActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = BlockActivityModule.class)
public interface BlockActivityComponent {
    AppCompatActivity activity();
    BlockControl.BlockView view();
    void inject(BlockActivity activity);
}
