package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.BlockActivityModule;
import com.banshengyuan.feima.dagger.module.BlockFragmentModule;
import com.banshengyuan.feima.view.fragment.BlockFairFragment;
import com.banshengyuan.feima.view.fragment.BlockHotFragment;
import com.banshengyuan.feima.view.fragment.BlockShopFragment;

import dagger.Component;

/**
 * Created by helei on 2017/5/3.
 * FragmentComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {BlockActivityModule.class, BlockFragmentModule.class})
public interface BlockFragmentComponent extends BlockActivityComponent{
    AppCompatActivity activity();
    void inject(BlockHotFragment hotFragment);
    void inject(BlockFairFragment blockFairFragment);
    void inject(BlockShopFragment blockShopFragment);
}
