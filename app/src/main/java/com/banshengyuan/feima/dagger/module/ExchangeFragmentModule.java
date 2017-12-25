package com.banshengyuan.feima.dagger.module;


import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.view.PresenterControl.CelebrityControl;
import com.banshengyuan.feima.view.PresenterControl.CommentControl;
import com.banshengyuan.feima.view.PresenterControl.FollowControl;
import com.banshengyuan.feima.view.PresenterControl.LoadDataView;
import com.banshengyuan.feima.view.PresenterControl.TrendsControl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/5/3.
 * FragmentModule
 */
@Module
public class ExchangeFragmentModule {
    private final AppCompatActivity activity;

    private TrendsControl.TrendsView mTrendsView;
    private FollowControl.FollowView mFollowView;
    private CelebrityControl.CelebrityView mCelebrityView;
    private CommentControl.CommentView mCommentView;

    public ExchangeFragmentModule(LoadDataView view, AppCompatActivity activity) {
        this.activity = activity;
        if (view instanceof TrendsControl.TrendsView) {
            mTrendsView = (TrendsControl.TrendsView) view;
        } else if (view instanceof FollowControl.FollowView) {
            mFollowView = (FollowControl.FollowView) view;
        } else if (view instanceof CelebrityControl.CelebrityView) {
            mCelebrityView = (CelebrityControl.CelebrityView) view;
        } else if (view instanceof CommentControl.CommentView) {
            mCommentView = (CommentControl.CommentView) view;
        }
    }

    @Provides
    @PerActivity
    TrendsControl.TrendsView trendsView() {
        return this.mTrendsView;
    }

    @Provides
    @PerActivity
    FollowControl.FollowView followView() {
        return this.mFollowView;
    }

    @Provides
    @PerActivity
    CelebrityControl.CelebrityView CelebrityView() {
        return this.mCelebrityView;
    }

    @Provides
    @PerActivity
    CommentControl.CommentView CommentView() {
        return this.mCommentView;
    }

}
