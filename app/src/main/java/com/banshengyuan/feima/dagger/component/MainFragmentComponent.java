package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.MainActivityModule;
import com.banshengyuan.feima.dagger.module.MainFragmentModule;
import com.banshengyuan.feima.view.fragment.MagicMusicFragment;
import com.banshengyuan.feima.view.fragment.MainFairFragment;
import com.banshengyuan.feima.view.fragment.MainHotFragment;
import com.banshengyuan.feima.view.fragment.MainVistaFragment;
import com.banshengyuan.feima.view.fragment.RecommendFragment;

import dagger.Component;

/**
 * Created by helei on 2017/5/3.
 * FragmentComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {MainActivityModule.class, MainFragmentModule.class})
public interface MainFragmentComponent extends MainActivityComponent{
    AppCompatActivity activity();
    void inject(RecommendFragment recommendFragment);
    void inject(MainFairFragment mainFairFragment);
    void inject(MainHotFragment hotFragment);
    void inject(MainVistaFragment vistaFragment);
    void inject(MagicMusicFragment magicMusicFragment);
}
