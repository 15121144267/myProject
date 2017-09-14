package com.banshengyuan.feima.dagger.component;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.banshengyuan.feima.dagger.ComponetGraph;
import com.banshengyuan.feima.dagger.module.ApplicationModule;
import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.gen.DaoSession;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.SharePreferenceUtil;
import com.banshengyuan.feima.view.model.ModelTransform;
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
    ImageLoaderHelper imageLoaderHelper();
    Gson gson();
    ModelTransform modeTransform();
    AMapLocationClient aMapLocationClient();
    AMapLocationClientOption aMapLocationClientOption();
}
