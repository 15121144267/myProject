package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.gen.DaoSession;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.MyOrderApi;
import com.banshengyuan.feima.network.networkapi.NoticeApi;
import com.banshengyuan.feima.view.PresenterControl.NoticeCenterControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterNoticeCenterImpl;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.banshengyuan.feima.view.model.MyOrderModel;
import com.banshengyuan.feima.view.model.NoticeCenterModel;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * NoticeCenterActivityModule
 */
@Module
public class NoticeCenterActivityModule {
    private final AppCompatActivity activity;
    private final NoticeCenterControl.NoticeCenterView view;

    public NoticeCenterActivityModule(AppCompatActivity activity, NoticeCenterControl.NoticeCenterView view) {
        this.activity = activity;
        this.view = view;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    NoticeCenterControl.NoticeCenterView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    NoticeCenterModel provideNoticeCenterModel(Gson gson, ModelTransform modelTransform) {
//        return new NoticeCenterModel(noticeApi,transform);//DaoSession daoSession daoSession.getOrderNoticeDao()
        return new NoticeCenterModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isToJson(false)
                .builder()
                .create(NoticeApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    NoticeCenterControl.PresenterNoticeCenter providePresenterNoticeCenter(PresenterNoticeCenterImpl presenter) {
        return presenter;
    }
}
