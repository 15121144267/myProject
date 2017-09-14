package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.gen.DaoSession;
import com.banshengyuan.feima.view.PresenterControl.NoticeCenterControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterNoticeCenterImpl;
import com.banshengyuan.feima.view.model.NoticeCenterModel;

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

    public NoticeCenterActivityModule(AppCompatActivity activity,NoticeCenterControl.NoticeCenterView view) {
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
    NoticeCenterModel provideNoticeCenterModel(DaoSession daoSession) {
        return new NoticeCenterModel(daoSession.getOrderNoticeDao());
    }

    @Provides
    @PerActivity
    NoticeCenterControl.PresenterNoticeCenter providePresenterNoticeCenter(PresenterNoticeCenterImpl presenter) {
        return presenter;
    }
}
