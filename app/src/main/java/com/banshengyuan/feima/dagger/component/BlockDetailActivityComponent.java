package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.BlockDetailActivityModule;
import com.banshengyuan.feima.view.PresenterControl.BlockDetailControl;
import com.banshengyuan.feima.view.activity.BlockDetailActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = BlockDetailActivityModule.class)
public interface BlockDetailActivityComponent {
    AppCompatActivity activity();
    BlockDetailControl.BlockDetailView view();
    void inject(BlockDetailActivity addressActivity);
}
