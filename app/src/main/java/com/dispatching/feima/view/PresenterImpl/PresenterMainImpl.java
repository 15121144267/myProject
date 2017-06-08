package com.dispatching.feima.view.PresenterImpl;

import com.dispatching.feima.utils.SharePreferenceUtil;
import com.dispatching.feima.view.PresenterControl.MainControl;
import com.dispatching.feima.view.model.MainModel;

import javax.inject.Inject;

/**
 * Created by helei on 2017/4/27.
 * PresenterMainImpl
 */

public class PresenterMainImpl implements MainControl.PresenterMain {
    private MainControl.MainView mView;
    private MainModel mMainModel;
    @Inject
    public PresenterMainImpl(MainModel model, SharePreferenceUtil sharePreferenceUtil) {
        mMainModel = model;
    }

    @Override
    public void requestNoticeCount() {
        mMainModel.queryNoticeDb().compose(mView.applySchedulers()).subscribe(this::querySuccess,
                throwable -> mView.showErrMessage(throwable));
    }

    private void querySuccess(Integer count){
        mView.querySuccess(count);
    }

    @Override
    public void setView(MainControl.MainView mainView) {
        mView = mainView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
