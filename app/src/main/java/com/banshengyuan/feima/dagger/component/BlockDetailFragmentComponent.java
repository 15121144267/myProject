package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.BlockDetailActivityModule;
import com.banshengyuan.feima.dagger.module.BlockDetailFragmentModule;
import com.banshengyuan.feima.view.fragment.BlockDetailFragment;

import dagger.Component;

/**
 * Created by helei on 2017/5/3.
 * FragmentComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {BlockDetailActivityModule.class, BlockDetailFragmentModule.class})
public interface BlockDetailFragmentComponent extends BlockDetailActivityComponent{
    AppCompatActivity activity();
    void inject(BlockDetailFragment blockDetailFragment);
}
