package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.SearchActivityModule;
import com.banshengyuan.feima.view.PresenterControl.SearchControl;
import com.banshengyuan.feima.view.activity.SearchActivity;

import dagger.Component;

/**
 * Created by helei on 2017/4/26.
 * LoginActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = SearchActivityModule.class)
public interface SearchActivityComponent {
    AppCompatActivity activity();
    SearchControl.SearchView view();
    SearchControl.PresenterSearch getPresenterSearch();
    void inject(SearchActivity activity);
}
