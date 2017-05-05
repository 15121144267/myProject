package com.dispatching.feima.dagger.component;

import android.content.Context;

import com.dispatching.feima.dagger.ComponetGraph;
import com.dispatching.feima.dagger.module.ApplicationModule;
import com.dispatching.feima.entity.BuProcessor;
import com.dispatching.feima.gen.DaoSession;
import com.dispatching.feima.utils.SharePreferenceUtil;
import com.dispatching.feima.view.model.ModelTransform;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent extends ComponetGraph {
    Context context();
    SharePreferenceUtil sharePreferenceUtil();
    DaoSession daoSession();
    BuProcessor buProcessor();
    Gson gson();
    ModelTransform modeTransform();
}
